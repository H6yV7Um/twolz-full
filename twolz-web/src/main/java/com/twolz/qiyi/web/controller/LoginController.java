package com.twolz.qiyi.web.controller;

import com.twolz.qiyi.common.core.BaseResultCode;
import com.twolz.qiyi.common.core.ResultDO;
import com.twolz.qiyi.common.core.SysCode;
import com.twolz.qiyi.common.exception.BizException;
import com.twolz.qiyi.common.service.sms.SmsService;
import com.twolz.qiyi.common.service.sms.SmsStatus;
import com.twolz.qiyi.domain.dto.BackPasswordDto;
import com.twolz.qiyi.domain.model.AdminUser;
import com.twolz.qiyi.web.aop.bind.Function;
import com.twolz.qiyi.web.aop.bind.Operate;
import com.twolz.qiyi.web.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liuwei
 * 后台登录
 *
 */
@Slf4j
@Controller
public class LoginController {

    @Autowired
    AdminUserService adminService;

    @Autowired
    SmsService smsService;

    @RequestMapping(value = "/")
    public String index() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:main";
        }
        return "login";
    }

    @RequestMapping(value = "/main")
    public String main() {
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @Operate(value="用户登录")
    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    public ResultDO<?> loginPost(@RequestBody AdminUser adminUser)  throws BizException {
        log.info("post login in");
        ResultDO result = new ResultDO();
        result.setSuccess(false);
        if (StringUtils.isEmpty(adminUser.getMobile())) {
            log.info("用户名不能为空");
            throw new BizException(SysCode.LOGIN_USER_NAME_EMPTY);
        }
        if (StringUtils.isEmpty(adminUser.getPasswd())) {
            log.info("密码不能为空");
            throw new BizException(SysCode.LOGIN_PASSWORD_EMPTY);
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(adminUser.getMobile().toString(), adminUser.getPasswd());
            subject.login(token);
            result.setSuccess(true);
        } catch (UnknownAccountException e) {
            log.error("登录失败：用户名或密码不正确[{}]",adminUser.getMobile());
            throw new BizException(SysCode.LOGIN_UN_OR_PWD_INCORRECT);
        } catch (IncorrectCredentialsException e) {
            log.info("登录失败：用户名或密码不正确[{}]",adminUser.getMobile());
            throw new BizException(SysCode.LOGIN_UN_OR_PWD_INCORRECT);
        } catch (BizException e) {
            throw e;
        } catch (RuntimeException e) {
            log.error("登录失败：未知错误,请联系管理员[{}]"+ e,adminUser.getMobile());
            throw new BizException(SysCode.COMMON_SERVICE_ERROR);
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/backPassword", method = RequestMethod.POST)
    public ResultDO<?> backPassword(@RequestBody BackPasswordDto backPasswordDto)  throws BizException {

        String mobile = backPasswordDto.getMobile().trim();
        String authCode = backPasswordDto.getAuthCode().trim();
        String password = backPasswordDto.getPassword().trim();
        String againPassword = backPasswordDto.getAgainPassword().trim();

        ResultDO result = new ResultDO();
        result.setSuccess(false);
        if (StringUtils.isEmpty(mobile)) {
            log.error("手机号码不能为空");
            throw new BizException(SysCode.BACKPWD_MOBILE_EMPTY);
        }
        if (StringUtils.isEmpty(authCode)) {
            log.error("手机验证码不能为空");
            throw new BizException(SysCode.BACKPWD_AUTH_CODE_EMPTY);
        }
        if (StringUtils.isEmpty(password)) {
            log.error("新密码不能为空");
            throw new BizException(SysCode.BACKPWD_PASSWORD_EMPTY);
        }
        if (!password.equals(againPassword)) {
            log.error("两次密码不一致");
            throw new BizException(SysCode.BACKPWD_PASSWORD_NEQ);
        }
        String resultCode = smsService.confirmSmsCaptcha(mobile,authCode);
        if(resultCode.equals(SysCode.SMS_OUT_VALID_TIME)){
            log.error("验证码已失效");
            throw new BizException(SysCode.SMS_OUT_VALID_TIME);
        }
        if (!resultCode.equals(BaseResultCode.TRUE)){
            log.error("验证码错误");
            throw new BizException(SysCode.BACKPWD_AUTH_CODE_INCORRECT);
        }
        AdminUser adminUser = adminService.selectByMobile(mobile);
        if (adminUser == null){
            log.error("用户不存在");
            throw new BizException(SysCode.COMMON_ERROR_FORMAT);
        }
        if (adminUser.getStatus() != 1){
            log.error("用户账号已停用");
            throw new BizException(SysCode.BACKPWD_USER_NAME_DISABLE);
        }
        AdminUser user = new AdminUser();
        String hashPassword = BCrypt.hashpw(password,adminUser.getSalt());
        user.setPasswd(hashPassword);
        user.setId(adminUser.getId());
        result.setSuccess(true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/sendBackPwdMsg", method = RequestMethod.POST)
    public ResultDO<?> sendBackPwdMsg(@RequestBody BackPasswordDto backPasswordDto)  throws BizException {
        String mobile = backPasswordDto.getMobile();
        ResultDO<String> result = new ResultDO<>();

        AdminUser sysAdminUser = adminService.selectByMobile(mobile);
        if(sysAdminUser == null){
            log.error("用户不存在");
            throw new BizException(SysCode.LOGIN_UN_OR_PWD_INCORRECT);
        }
        if(sysAdminUser.getStatus() != 1){
            log.error("用户账号已停用");
            throw new BizException(SysCode.BACKPWD_USER_NAME_DISABLE);
        }
        try{
            String resultCode = smsService.allownSend(mobile);
            if(resultCode != BaseResultCode.TRUE){
                throw new BizException(BaseResultCode.SEND_SMS_ERROR);
            }
        } catch (RuntimeException e) {
            log.error("发送验证短信：",e);
            throw new BizException(e.getMessage());
        }
        try {
            String randomCode = RandomStringUtils.randomNumeric(6);

            if (smsService.sendCodeSMS(mobile,randomCode)){
                SmsStatus smsStatus = new SmsStatus(mobile.toString());
                smsStatus.setLastRandom(String.valueOf(randomCode));
                String resultCode = smsService.updateSum(smsStatus);
                if(resultCode != BaseResultCode.TRUE){
                    result.setError(resultCode);
                    return result;
                } else {
                    result.setSuccess(true);
                }
            } else {
                log.info(mobile+"：发送找回密码短信失败");
                result.setSuccess(false);
                result.setResultCode(BaseResultCode.SEND_SMS_ERROR);
            }

        } catch (Exception e) {
            log.error("发送短信失败：",e);
            result.setSuccess(false);
            result.setResultCode(BaseResultCode.SEND_SMS_ERROR);
            result.setErrorMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/unauthor")
    public String unauthor(HttpServletRequest request, HttpServletResponse response) {
        String xmlHttpRequest = request.getHeader("X-Requested-With");
        if ( xmlHttpRequest != null && xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {//是ajax请求
            return "redirect:/unauthorAjax";
        }
        return "unauthor";
    }

    @RequestMapping("/unauthorAjax")
    @ResponseBody
    public ResponseEntity<ResultDO> unauthorAjax() {
        ResultDO result = new ResultDO();
        return new ResponseEntity<ResultDO>(result, HttpStatus.UNAUTHORIZED);
    }
}
