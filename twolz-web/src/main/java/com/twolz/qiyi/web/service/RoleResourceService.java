package com.twolz.qiyi.web.service;

import com.twolz.qiyi.domain.mapper.RoleResourceMapper;
import com.twolz.qiyi.domain.model.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuwei
 * @date 2018-06-03
 */
@Service
public class RoleResourceService {

    @Autowired
    RoleResourceMapper roleResourceMapper;

    public void delByResourceId(final Integer resourceId){
        roleResourceMapper.delByResourceId(resourceId);
    }

    public void insert(RoleResource record){
        roleResourceMapper.insert(record);
    }

    public List<Integer> selectIdByRoleId(final Integer roleId){
        return roleResourceMapper.selectIdByRoleId(roleId);
    }
}
