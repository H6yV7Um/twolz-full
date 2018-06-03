package com.twolz.qiyi.web.service;

import com.twolz.qiyi.domain.mapper.UserRoleMapper;
import com.twolz.qiyi.domain.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuwei
 * @date 2018-06-03
 */
@Service
public class UserRoleService {

    @Autowired
    UserRoleMapper userRoleMapper;

    public void insertBatch(List<UserRole> list){
        userRoleMapper.insertBatch(list);
    }

    public void deleteByUserId(Integer userId){
        userRoleMapper.deleteByUserId(userId);
    }

    public List<Integer> selectByUserId(Integer userId){
        return userRoleMapper.selectByUserId(userId);
    }
}
