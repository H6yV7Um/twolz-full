package com.twolz.qiyi.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twolz.qiyi.common.constant.Constants;
import com.twolz.qiyi.common.dto.CurrentUserInfo;
import com.twolz.qiyi.common.dto.TokenDto;
import com.twolz.qiyi.common.exception.UnknownLoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by liuwei on 2017/3/14.
 */
@Slf4j
@Service
public class AuthTokenService {

    private @Value("${system.api.token.app-secret}") String appSsecret;

    private @Value("${system.api.token.app-issuer}") String appIssuer;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 校验token是否合法
     * @param token
     * @return
     *
     * */
    public boolean verifyToken(String token){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(appSsecret))
                    .withIssuer(appIssuer)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * 解密token
     * @param token
     * @return
     *
     * */
    public TokenDto descToken(String token) {
        TokenDto tokenDto = new TokenDto();
        JWT jwt = JWT.decode(token);
        tokenDto.setAppkey(jwt.getClaim(Constants.APP_KEY).asString());
        tokenDto.setSecret(jwt.getClaim(Constants.APP_SECRET).asString());
        return tokenDto;
    }

    public String getToken(String appkey,String secret) throws Exception{
        String token = JWT.create()
                .withIssuer(appIssuer)
                .withClaim(Constants.APP_KEY, appkey)
                .withClaim(Constants.APP_SECRET,secret)
                .sign(Algorithm.HMAC256(appSsecret));
        return token;
    }

    public CurrentUserInfo authLogin(String token, String tokenType) throws UnknownLoginException, IOException {

        TokenDto tokenDto = descToken(token);
        if (tokenDto == null || StringUtils.isEmpty(tokenDto.getAppkey()) || StringUtils.isEmpty(tokenDto.getSecret())) {
            throw new UnknownLoginException("无效凭证，请重新登录");
        }
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        String json = valueOperations.get(tokenType+tokenDto.getAppkey());
        if(StringUtils.isBlank(json)){
            throw new UnknownLoginException("无效凭证，请重新登录");
        }
        ObjectMapper mapper = new ObjectMapper();
        CurrentUserInfo currentUserInfo = mapper.readValue(json,CurrentUserInfo.class);
        if(tokenDto.getAppkey() != null && currentUserInfo != null && tokenDto.getSecret().equals(currentUserInfo.getSecret())){
            return currentUserInfo;
        } else {
            throw new UnknownLoginException("无效凭证，请重新登录");
        }
    }
}
