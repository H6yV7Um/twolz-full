package com.twolz.qiyi.domain.mapper;

import com.twolz.qiyi.domain.model.AdminUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdminUserMapper extends Mapper<AdminUser> {

    AdminUser selectByMobile(@Param("mobile") String phone);

    List<AdminUser> selectBySearch(@Param("search")String search,@Param("userId")Integer userId);

    int selectMobileIsExists(@Param("mobile") String mobile,@Param("id") Integer id);
}