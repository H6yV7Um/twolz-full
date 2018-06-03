package com.twolz.qiyi.web.controller;

import com.github.pagehelper.PageInfo;
import com.twolz.qiyi.common.core.ResultDO;
import com.twolz.qiyi.common.core.SysCode;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.domain.dto.AdminUserDto;
import com.twolz.qiyi.domain.model.AdminUser;
import com.twolz.qiyi.domain.model.Role;
import com.twolz.qiyi.web.aop.bind.Function;
import com.twolz.qiyi.web.aop.bind.Operate;
import com.twolz.qiyi.web.security.realm.ShiroUser;
import com.twolz.qiyi.web.service.AdminUserService;
import com.twolz.qiyi.web.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liuwei
 * date 2017-04-06
 * 用户管理员控制器
 */
@Slf4j
@Function(value ="账号管理",moduleName = "系统管理",subModuleName = "")
@Controller
@RequestMapping("/sys/user")
public class AdminUserController
    {
    @Autowired
    AdminUserService adminUserService;

    @Autowired
    RoleService roleService;

    @RequestMapping(value = "/")
    public String index(Model model) throws BizException {
        List<Role> roleList = roleService.selectByAll();
        model.addAttribute("roleList",roleList);
        return "sys/user/list";
    }

    @Operate(value="账号列表")
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public ResultDO<?> list(@RequestBody AdminUserDto adminUserDto) throws BizException {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        ResultDO<PageInfo<AdminUser>> result = new ResultDO<>();
        boolean isAdmin = shiroUser.getRoleSet().contains("1");
        Integer userId=0;
        if (!shiroUser.getRoleSet().contains("1")) {
            userId = shiroUser.getId();
        }
        result.setModule(adminUserService.findByPage(adminUserDto,userId));
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/info/{id}",method = RequestMethod.GET)
    public ResultDO<?> info(@PathVariable("id") final int id) throws BizException {
        ResultDO<AdminUserDto> result = new ResultDO<>();
        result.setSuccess(true);
//        result.setModule(adminUserService.selectById(id));
        return result;
    }

    @Operate(value="添加账号")
    @ResponseBody
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultDO<?> add(@RequestBody AdminUserDto adminUserDto) throws BizException {
        ResultDO<PageInfo<AdminUser>> result = new ResultDO<>();
        //获取用户手机号码是否存在
        int cnt = adminUserService.getMobileIsExists(adminUserDto.getMobile(),adminUserDto.getId());
        if(cnt == 0){
            adminUserService.addAdminUser(adminUserDto);
            result.setSuccess(true);
        }else{
            throw new BizException(SysCode.USER_MOBILE_EXISTS);
        }
        return result;
    }

    @Operate(value="修改账号")
    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultDO<?> update(@RequestBody AdminUserDto adminUserDto) throws BizException {
        ResultDO<PageInfo<AdminUser>> result = new ResultDO<>();
        //获取用户手机号码是否存在
        int cnt = adminUserService.getMobileIsExists(adminUserDto.getMobile(),adminUserDto.getId());
        if(cnt == 0){
            adminUserService.updateAdminUser(adminUserDto);
            result.setSuccess(true);
        }else{
            throw new BizException(SysCode.USER_MOBILE_EXISTS);
        }
        return result;
    }

    @Operate(value="删除账号")
    @ResponseBody
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public ResultDO<?> delete(@PathVariable("id") final int id) throws BizException {
            ResultDO<AdminUser> result = new ResultDO<>();
        adminUserService.deleteById(id);
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/getRoleList",method = RequestMethod.POST)
    public ResultDO<?> getRoleList() throws BizException {
        ResultDO<List<Role>> result = new ResultDO<>();
        List<Role> roleList = roleService.selectByAll();
        result.setModule(roleList);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/initPwd")
    public String changePwdIndex(Model model) throws BizException {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("phone",shiroUser.getMobile());
        return "sys/user/password";
    }

//    @ResponseBody
//    @RequestMapping(value = "/checkPwd",method = RequestMethod.POST)
//    public ResultDO<?> checkPwd(@RequestBody ChangePasswordDto changePasswordDto) throws BizException {
//        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
//        ResultDO<?> result = new ResultDO<>();
//        boolean res = false;
//        if(BCrypt.checkpw(changePasswordDto.getPassword(),shiroUser.getPasswd())){
//            res = true;
//        }
//        result.setSuccess(res);
//        return result;
//    }

//    @Operate(value="修改密码",isLogParams = false)
//    @ResponseBody
//    @RequestMapping(value = "/changePwd",method = RequestMethod.POST)
//    public ResultDO<?> changePwd(@RequestBody ChangePasswordDto changePasswordDto) throws BizException {
//        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
//        ResultDO<?> result = new ResultDO<>();
//        if(!BCrypt.checkpw(changePasswordDto.getPassword(),shiroUser.getPasswd())){
//            //验证旧密码是否正确
//            throw new BizException(SysCode.CHANGEPWD_PWD_INCORRECT);
//        }
//        if(StringUtils.isEmpty(changePasswordDto.getNewpassword())){
//            //验证新密码是否为空
//            throw new BizException(SysCode.CHANGEPWD_PASSWORD_EMPTY);
//        }
//        if(changePasswordDto.getPassword().equals(changePasswordDto.getNewpassword())){
//            //新密码不能与旧密码相同
//            throw new BizException(SysCode.OLD_PASSWORD_NEQ);
//        }
//        if(!changePasswordDto.getNewpassword().equals(changePasswordDto.getNewpasswordagain())){
//            //验证两次新密码是否一致
//            throw new BizException(SysCode.CHANGEPWD_PASSWORD_NEQ);
//        }
//        AdminUser sysAdminUser = adminUserService.selectByMobile(shiroUser.getMobile().toString());
//        AdminUser user = new AdminUser();
//        user.setId(sysAdminUser.getId());
//        String hashPwd = BCrypt.hashpw(changePasswordDto.getNewpassword(),sysAdminUser.getSalt());
//        user.setPasswd(hashPwd);
//
//        adminUserService.updataPwdById(user);
//        UsernamePasswordToken token = new UsernamePasswordToken(shiroUser.getMobile().toString(), changePasswordDto.getNewpassword());
//        SecurityUtils.getSubject().login(token);
//        result.setSuccess(true);
//        return result;
//    }
}
