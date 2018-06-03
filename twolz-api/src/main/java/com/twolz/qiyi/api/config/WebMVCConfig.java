package com.twolz.qiyi.api.config;

import com.twolz.qiyi.api.web.bind.support.CurrentUserHandlerMethodArgumentResolver;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.util.List;


/**
 * Created by liuwei
 * date: 2017-03-20
 */
@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("50MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }

    @Bean
    public CurrentUserHandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver() {
        return new CurrentUserHandlerMethodArgumentResolver();
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserHandlerMethodArgumentResolver());
    }

}