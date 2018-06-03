package com.twolz.qiyi.api.web.bind.support;

import com.twolz.qiyi.api.web.bind.annotation.CurrentUser;
import com.twolz.qiyi.common.constant.Constants;
import com.twolz.qiyi.common.dto.CurrentUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by liuwei
 * date 2017-03-20
 */
@Slf4j
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        if(webRequest.getNativeRequest() instanceof HttpServletRequest){
            final HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            try {
                CurrentUserInfo currentUserInfo= (CurrentUserInfo) request.getAttribute(Constants.APP_USER);
                if(currentUserInfo!=null){
                    return currentUserInfo;
                }
            } catch (Exception ule){
                log.error("user is null");
            }
        }
        return  WebArgumentResolver.UNRESOLVED;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(CurrentUser.class) != null;
    }
}
