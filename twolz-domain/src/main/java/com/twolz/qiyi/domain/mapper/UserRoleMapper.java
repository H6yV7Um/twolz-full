package com.twolz.qiyi.domain.mapper;

import com.twolz.qiyi.domain.model.UserRole;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole> {

    void insertBatch(List<UserRole> list);

    void deleteByUserId(@Param("userId") Integer userId);

    void deleteByRoleId(@Param("roleId") Integer roleId);

    List<Integer> selectByUserId(@Param("userId") Integer userId);
}