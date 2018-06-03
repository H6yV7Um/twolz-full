package com.twolz.qiyi.web.service;

import com.twolz.qiyi.common.constant.SysConst;
import com.twolz.qiyi.domain.mapper.ResourceMapper;
import com.twolz.qiyi.domain.model.Resource;
import com.twolz.qiyi.domain.model.Role;
import com.twolz.qiyi.domain.model.RoleResource;
import com.twolz.qiyi.web.dto.ResourceTreeDto;
import com.twolz.qiyi.web.dto.ResourceTreeMenu;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author liuwei
 * @date 2018-06-03
 */
@Service
public class ResourceService {

    @Autowired
    ResourceMapper resourceMapper;

    @Autowired
    RoleResourceService roleResourceService;

    @Autowired
    UserRoleService userRoleService;

    /**
     * 查询所有URL和权限不为空的资源信息
     * return
     * */
    public List<Resource> selectAllByUrlNotNull() {
        return resourceMapper.selectAllByUrlNotNull();
    }

    public Resource findResourceById(Integer id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    /**
     * 添加资源信息
     * @param resource
     *
     * */
    @Transactional
    public void insertResouce(Resource resource) {

        String pids = this.selectParentIdsById(resource.getParentId());
        resource.setParentIds(org.apache.commons.lang3.StringUtils.isEmpty(pids) ? resource.getParentId() + "" : (pids + "," + resource.getParentId()));
        resourceMapper.insertSelective(resource);

        //给超级管理员自动赋予权限
        RoleResource record = new RoleResource();
        record.setResourceId(resource.getId());
        record.setRoleId(1);
        roleResourceService.insert(record);
    }

    /**
     * 修改资源
     * @param resource
     *
     * */
    public int updateResouce(Resource resource) {
        String pids = this.selectParentIdsById(resource.getParentId());
        resource.setParentIds(StringUtils.isEmpty(pids) ? resource.getParentId() + "" : (pids + "," + resource.getParentId()));
        return resourceMapper.updateByPrimaryKeySelective(resource);
    }

    /**
     * 删除资源
     * @param id
     *
     * */
    @Transactional
    public void delResource(final Integer id){
        resourceMapper.deleteByPrimaryKey(id);
        //清除角色资源关联信息表
        roleResourceService.delByResourceId(id);

    }

    public List<Resource> selectByRoleId(final Integer roleId) {
        return resourceMapper.selectByRoleId(roleId);
    }

    public List<ResourceTreeMenu> selectMenuTree(List<Role> roleList) {
        StringBuffer roleIds = new StringBuffer();
        for (int i=0;i<roleList.size();i++) {
            if (i==0){
                roleIds.append(roleList.get(i).getId());
            } else {
                roleIds.append(","+roleList.get(i).getId());
            }
        }
        List<Resource> resourceList = resourceMapper.selectMenuByRoleList(roleIds.toString());
        return buildMenuTreeData(resourceList);
    }

    /**
     * 获取角色拥有的资源树
     * @param roleId
     * @param userName
     * return
     *
     * */
    public List<ResourceTreeDto> getTreeData(final int roleId, final String userName, final int userId) {
        List<Resource> dataList = new ArrayList<>();
        //判断该用户是否为超级管理员,如果是超级管理员则查询出所有资源来,如果不是则查询该用户所拥有的全部资源
        if (userName.equals(SysConst.SYSTEM_ADMIN)){
            dataList = resourceMapper.selectAll();
        } else {
            List<Integer> roleList = userRoleService.selectByUserId(userId);
            if(roleList!=null && roleList.size()>0){
                dataList = resourceMapper.selectByRoleList(roleList);
            }
        }

        List<Integer> rList = roleResourceService.selectIdByRoleId(roleId);
        List<ResourceTreeDto> treeList = new ArrayList<>();
        for (Resource resource : dataList) {
            ResourceTreeDto dto = new ResourceTreeDto();
            dto.setId(resource.getId());
            dto.setParent(resource.getParentId() == 0 ? "#" : resource.getParentId() + "");
            dto.setText(resource.getName());
            dto.setSort(resource.getSort());
            if (StringUtils.isEmpty(resource.getIcon())){
                if (!checkIsExistChildNode(dataList,resource.getId())){
                    dto.setIcon("glyphicon glyphicon-folder-open");
                } else {
                    dto.setIcon("glyphicon glyphicon-file");
                }
            } else {
                dto.setIcon(resource.getIcon());
            }
            if (rList.contains(resource.getId()) && checkIsExistChildNode(dataList,resource.getId())){
                dto.setSelected(true);
            }
            dto.setOpened(true);
            treeList.add(dto);
        }
        Collections.sort(treeList, new Comparator<ResourceTreeDto>() {
            @Override
            public int compare(ResourceTreeDto o1, ResourceTreeDto o2) {
                return o1.getSort().compareTo(o2.getSort());
            }
        });
        return treeList;
    }


    /**
     * 生成菜单树
     *
     * */
    public List<ResourceTreeMenu> buildMenuTreeData(List<Resource> resourceList) {
        List<ResourceTreeMenu> dataList = new ArrayList<>();
        resourceList.forEach(resource -> {
            ResourceTreeMenu resourceTreeMenu = new ResourceTreeMenu();
            resourceTreeMenu.setId(resource.getId());
            resourceTreeMenu.setParentId(resource.getParentId());
            resourceTreeMenu.setTitle(resource.getName());
            resourceTreeMenu.setHref(resource.getUrl());
            resourceTreeMenu.setIcon(resource.getIcon());
            dataList.add(resourceTreeMenu);
        });
        List<ResourceTreeMenu> nodeList = new ArrayList<>();
        dataList.forEach(r1 -> {
            boolean mark = false;
            for (ResourceTreeMenu r2 : dataList) {
                if (r1.getParentId().equals( r2.getId())) {
                    mark = true;
                    if (r2.getChildren() == null) {
                        r2.setChildren(new ArrayList<ResourceTreeMenu>());
                    }
                    r2.getChildren().add(r1);
                    break;
                }
            }
            if (!mark) {
                nodeList.add(r1);
            }
        });
        return nodeList;
    }

    /**
     * 根据资源ID查询所有父级资源ID集合（1,2,3）
     * @param id
     *
     * */
    public String selectParentIdsById(int id) {
        return resourceMapper.selectParentIdsById(id);
    }

    /**
     * 将资源列表重新排序为递归树
     * return
     *
     */
    public List<Resource> selectResource() {
        List<Resource> resourceList = new ArrayList<>();
        List<Resource> _resourceList = resourceMapper.selectAll();
        for (Resource resource : _resourceList) {
            if (resource.getParentId() == 0) {
                resourceList.add(resource);
                if (!checkIsExistChildNode(_resourceList, resource.getId())) {
                    List<Resource> childrens = new ArrayList<>();
                    childrens = getChildList(_resourceList, resource.getId(), childrens);
                    resourceList.addAll(childrens);
                }
            }
        }
        return resourceList;
    }

    /**
     * 判断当前节点是(false)否(true)存在子节点
     * @param resources
     * @param id
     * @return
     *
     */
    public boolean checkIsExistChildNode(List<Resource> resources, int id) {
        boolean flag = true;
        for (Resource resource : resources) {
            if (resource.getParentId() == id) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 递归加载子节点
     * @param resourceList
     * @param pid
     * @param childList
     * @return
     *
     */
    public List<Resource> getChildList(List<Resource> resourceList, Integer pid, List<Resource> childList) {
        for (Resource resource : resourceList) {
            if (resource.getParentId().equals(pid)) {
                childList.add(resource);
                if (!checkIsExistChildNode(resourceList, resource.getId())) {
                    getChildList(resourceList, resource.getId(), childList);
                }
            }
        }
        return childList;
    }
}
