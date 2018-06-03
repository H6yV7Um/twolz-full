package com.twolz.qiyi.domain.mapper;

import com.twolz.qiyi.domain.model.RoleResource;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleResourceMapper extends Mapper<RoleResource> {

    void insertBatch(List<RoleResource> list);

    int delByResourceId(@Param("resourceId") Integer resourceId);

    int delByRoleId(@Param("roleId") Integer roleId);

    List<Integer> selectIdByRoleId(@Param("roleId") Integer roleId);

}