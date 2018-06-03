package com.twolz.qiyi.api.interceptor;

/**
 * Created by liuwei
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twolz.qiyi.api.service.AuthTokenService;
import com.twolz.qiyi.common.constant.Constants;
import com.twolz.qiyi.common.core.ResultDO;
import com.twolz.qiyi.common.dto.CurrentUserInfo;
import com.twolz.qiyi.common.exception.UnknownLoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liuwei
 * @date 2018-05-23
 */
@Configuration
public class ExplorerInterceptor extends WebMvcConfigurerAdapter {

    /**
     * 配置拦截器
     * @author steven
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(encodeHandlerInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(authHandlerInterceptor()).addPathPatterns("/**/authApi/**");
    }

    @Bean
    public EncodeHandlerInterceptor encodeHandlerInterceptor() {
        return new EncodeHandlerInterceptor();
    }

    @Bean
    public AuthHandlerInterceptor authHandlerInterceptor() {
        return new AuthHandlerInterceptor();
    }


}

@Slf4j
class AuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    AuthTokenService authTokenService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if (method.equals("OPTIONS")){
            return true;
        }
        String token = request.getHeader("token");

        if(StringUtils.isBlank(token)){
            onLoginFail(response,"无效凭证，请重新登录！");
            return false;
        }
        log.debug("auth is :"+token);

        if(!authTokenService.verifyToken(token)){
            onLoginFail(response,"无效凭证，请重新登录！");
            return false;
        }
        try {
            CurrentUserInfo currentUserInfo = authTokenService.authLogin(token,Constants.APP_TOKEN);
            request.setAttribute(Constants.APP_USER,currentUserInfo);
        } catch (UnknownLoginException ule){
            onLoginFail(response,ule.getMessage());
            return false;
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    private void onLoginFail(HttpServletResponse response, String msg) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResultDO<?> result = new ResultDO<>();
        result.setSuccess(false);
        result.setResultCode("-99");
        result.setErrorMessage(msg);
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(result));
    }


    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}


@Slf4j
class EncodeHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }

}
