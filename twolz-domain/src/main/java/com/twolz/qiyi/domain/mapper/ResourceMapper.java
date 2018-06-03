package com.twolz.qiyi.domain.mapper;

import com.twolz.qiyi.domain.model.Resource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ResourceMapper extends Mapper<Resource> {

    List<Resource> selectAllByUrlNotNull();

    List<Resource> selectAll();

    /**
     * 根据用户权限集合查询菜单资源集合
     * @param roleIds
     * */
    List<Resource> selectMenuByRoleList(@Param("roleIds")String roleIds);

    /**
     * 根据角色查询菜单资源集合
     * @param roleId
     * */
    List<Resource> selectByRoleId(@Param("roleId") Integer roleId);

    String selectParentIdsById(@Param("pid") Integer pid);

    List<Resource> selectByRoleList(List<Integer> list);

}