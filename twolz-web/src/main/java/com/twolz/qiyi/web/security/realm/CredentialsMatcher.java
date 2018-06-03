package com.twolz.qiyi.web.security.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by chenyu on 2017/5/26.
 */
@Slf4j
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;
        //获得用户输入的密码
        String inPassword = new String(utoken.getPassword());
        //获得数据库中的密码
        String dbPassword = (String) info.getCredentials();
        //进行密码的比对
        boolean checkRes = BCrypt.checkpw(inPassword, dbPassword);

        if(checkRes) {
            UsernamePasswordToken userToken = (UsernamePasswordToken) token;
            String mobile = userToken.getUsername();
            //删除用户redis中权限缓存
            stringRedisTemplate.delete("" + mobile);
        }
        return checkRes;
    }
}
