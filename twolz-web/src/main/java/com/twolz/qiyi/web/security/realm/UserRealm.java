package com.twolz.qiyi.web.security.realm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twolz.qiyi.common.constant.Constants;
import com.twolz.qiyi.common.core.SysCode;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.domain.model.AdminUser;
import com.twolz.qiyi.domain.model.Resource;
import com.twolz.qiyi.domain.model.Role;
import com.twolz.qiyi.web.dto.UserPermsDto;
import com.twolz.qiyi.web.service.AdminUserService;
import com.twolz.qiyi.web.service.ResourceService;
import com.twolz.qiyi.web.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liuwei
 */
@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    AdminUserService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Shiro登录认证(原理：用户提交 用户名和密码  --- shiro 封装令牌 ---- realm 通过用户名将密码查询返回 ---- shiro 自动去比较查询出密码和用户输入密码是否一致---- 进行登陆控制 )
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.debug("Shiro开始数据库登录认证");
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String mobile = userToken.getUsername();
        AdminUser adminUser = adminService.selectByMobile(mobile);
        if (adminUser==null){
            log.info("用户名或密码不正确");
            throw new UnknownAccountException();
        }
        if (adminUser.getStatus() != 1){
            log.info("用户账号已停用");
            throw new BizException(SysCode.LOGIN_USER_NAME_DISABLE);
        }
        ShiroUser shiroUser = new ShiroUser();
        BeanUtils.copyProperties(adminUser,shiroUser);

        //清除缓存
        stringRedisTemplate.delete(Constants.USER_PERMS + shiroUser.getMobile());

        shiroUser = initRolesAndPermissions(shiroUser);
        // 认证缓存信息
        return new SimpleAuthenticationInfo(shiroUser, adminUser.getPasswd(), getName());
    }

    /**
     * Shiro访问链接权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        shiroUser = initRolesAndPermissions(shiroUser);
        Set<String> roleSet = shiroUser.getRoleSet();
        if(roleSet != null && roleSet.size() > 0){
            info.addRoles(shiroUser.getRoleSet());
        }
        Set<String> permissions = shiroUser.getPermissionSet();
        if(permissions != null && permissions.size() > 0){
            info.addStringPermissions(shiroUser.getPermissionSet());
        }
        return info;
    }

    private ShiroUser initRolesAndPermissions(ShiroUser shiroUser){
        ObjectMapper mapper = new ObjectMapper();
        UserPermsDto userPermsDto = new UserPermsDto();
        Set<String> roleSet = new HashSet<>();
        Set<String> permissions = new HashSet<>();

        try {
            //判断redis中是否有当前用户权限
            ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
            String json  = valueOperations.get(Constants.USER_PERMS + shiroUser.getMobile());

            if(StringUtils.isNotBlank(json)) {
                userPermsDto = mapper.readValue(json,UserPermsDto.class);
                roleSet = userPermsDto.getRoleSet();
                permissions = userPermsDto.getPermissions();
            } else {
                //没有则从mysql中获取
                List<Role> roleList = roleService.selectByUserId(shiroUser.getId());
                for (Role role : roleList) {
                    roleSet.add(role.getId().toString());
                    if(role != null){
                        List<Resource> _resource = resourceService.selectByRoleId(role.getId());
                        for(Resource resource : _resource){
                            if(resource != null && StringUtils.isNotEmpty(resource.getPermission())){
                                permissions.add(resource.getPermission());
                            }
                        }
                    }
                }
                userPermsDto.setRoleSet(roleSet);
                userPermsDto.setPermissions(permissions);
                valueOperations.set(Constants.USER_PERMS + shiroUser.getMobile(), mapper.writeValueAsString(userPermsDto));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        if(roleSet != null && roleSet.size() > 0){
            shiroUser.setRoleSet(roleSet);
        }
        if(permissions != null && permissions.size() > 0){
            shiroUser.setPermissionSet(permissions);
        }
        return shiroUser;
    }

}