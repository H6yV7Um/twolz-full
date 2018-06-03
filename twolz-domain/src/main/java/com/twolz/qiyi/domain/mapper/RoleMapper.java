package com.twolz.qiyi.domain.mapper;

import com.twolz.qiyi.domain.model.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    List<Role> selectByUserId(@Param("userId") Integer userId);

    List<Role> selectBySearch(@Param("searchVal") String searchVal);

    int selectIsExistRole(@Param("id") Integer id, @Param("roleName") String roleName, @Param("roleType") Integer roleType);

}