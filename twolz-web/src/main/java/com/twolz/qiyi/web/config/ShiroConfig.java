package com.twolz.qiyi.web.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.twolz.qiyi.common.config.RedisConfig;
import com.twolz.qiyi.domain.config.DatasourceConfig;
import com.twolz.qiyi.web.security.filter.CustomRolesAuthorizationFilter;
import com.twolz.qiyi.web.security.realm.CredentialsMatcher;
import com.twolz.qiyi.web.security.realm.UserRealm;
import com.twolz.qiyi.web.security.session.RedisSessionDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Created by liuwei
 * date: 2017-03-28
 */
@Slf4j
@Configuration
@AutoConfigureBefore({DatasourceConfig.class,RedisConfig.class})
public class ShiroConfig {

    //配置核心安全事务管理器
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm, SessionManager sessionManager) {
        log.debug("Setting security manager");
        final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //配置自定义的权限登录器
    @Bean(name="userRealm")
    public UserRealm userRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    //配置自定义的密码比较器
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        log.debug("Setting Shiro Filter");

        final ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/login");
        factoryBean.setUnauthorizedUrl("/unauthor");
        final LinkedHashMap<String, String> filterChains = new LinkedHashMap<>();
        filterChains.put("/css/**", CustomFilter.ANON);
        filterChains.put("/js/**", CustomFilter.ANON);
        filterChains.put("/img/**", CustomFilter.ANON);
        filterChains.put("/fonts/**", CustomFilter.ANON);
        filterChains.put("/plugins/**", CustomFilter.ANON);
        filterChains.put("/druid/**", CustomFilter.USER);
        filterChains.put("/logout", CustomFilter.LOGOUT);
        filterChains.put("/loginPost", CustomFilter.ANON);
//        filterChains.put("/**", CustomFilter.AUTHC);
        log.debug("Creating filter chain {}", filterChains);
        factoryBean.setFilterChainDefinitionMap(filterChains);
        final LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
        filters.put(CustomFilter.ANY_ROLES,anyRolesFilter());
        factoryBean.setFilters(filters);
        return factoryBean;
    }

    @Bean(name="anyRoles")
    public AuthorizationFilter anyRolesFilter(){
        CustomRolesAuthorizationFilter filter = new CustomRolesAuthorizationFilter();
        filter.setName("anyRoles");
        return filter;
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    @Configuration
    protected static class ShiroSessionConfiguration {

        @Autowired(required = false)
        Collection<SessionListener> sessionListeners;

        @Autowired
        private RedisTemplate redisTemplate;

        @Bean
        public SessionDAO sessionDAO() {
            return new RedisSessionDAO(redisTemplate);
        }

        @Bean
        public DefaultWebSessionManager sessionManager(@Qualifier("sessionCookie") SimpleCookie sessionCookie) {
            final DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            sessionManager.setGlobalSessionTimeout(1800000);
            sessionManager.setSessionDAO(sessionDAO());
            if (sessionListeners != null && !sessionListeners.isEmpty()) {
                sessionManager.setSessionListeners(sessionListeners);
            }
            sessionManager.setSessionValidationSchedulerEnabled(false);
            sessionManager.setSessionIdCookie(sessionCookie);
            return sessionManager;
        }
    }

    @Configuration
    protected static class ShiroCookieConfiguration {

        @Bean(name = "sessionCookie")
        public SimpleCookie sessionCookie() {
            final SimpleCookie sessionCookie = new SimpleCookie("sessionCookie");
            sessionCookie.setPath("/");
            sessionCookie.setHttpOnly(true);
            sessionCookie.setMaxAge(-1);
            return sessionCookie;
        }

        @Bean
        public SimpleCookie rememberCookie() {
            final SimpleCookie rememberCookie = new SimpleCookie("rememberCookie");
            rememberCookie.setPath("/");
            rememberCookie.setHttpOnly(true);
            rememberCookie.setMaxAge(7 * 24 * 60 * 60);
            return rememberCookie;
        }

        @Bean
        public RememberMeManager rememberMeManager(@Qualifier("rememberCookie") SimpleCookie rememberCookie) {
            final CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
            rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
            rememberMeManager.setCookie(rememberCookie);
            return rememberMeManager;
        }
    }

    @Configuration
    protected static class Processor {

        @Bean
        public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
            return new LifecycleBeanPostProcessor();
        }

        @Bean
        public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
            final DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
            proxyCreator.setProxyTargetClass(true);
            return proxyCreator;
        }
    }

    class CustomFilter {
        static final String ANON = "anon";
        static final String AUTHC = "authc";
        static final String LOGOUT = "logout";
        static final String USER = "user";
        static final String ANY_ROLES = "anyRoles";
    }
}
