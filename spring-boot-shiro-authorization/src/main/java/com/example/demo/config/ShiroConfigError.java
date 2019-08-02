package com.example.demo.config;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LiYingChun
 * @date 2019/8/1
 * 错误版本，用于后续学习
 */
/*
@Configuration
public class ShiroConfigError {

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager manager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 设置SecurityManager
        factoryBean.setSecurityManager(manager);
        // 登录url与登录成功后跳转的url
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/index");
        // 未认证
        factoryBean.setUnauthorizedUrl("/403");

        /*LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/**", "user");

        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置退出
        filterChainDefinitionMap.put("/logout", "logout");
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面;
//        filterChainDefinitionMap.put("/login", "anon");

        // 静态资源
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");

        // 其他请求都拦截，
        filterChainDefinitionMap.put("/**", "user");

        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultSecurityManager manager = new DefaultSecurityManager();
        manager.setRealm(shiroRealm());
        manager.setRememberMeManager(rememberMeManager());
        return manager;
    }


    @Bean
    public MyShiroRealm shiroRealm() {
        return new MyShiroRealm();
    }
*/
    /**
     * 设置启用Shiro权限相关注解
     */
    /*@Bean
    public AuthorizationAttributeSourceAdvisor sourceAdvisor(SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }*/

    /*@Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 10min
        sessionManager.setGlobalSessionTimeout(60 * 60 * 10);
        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return sessionManager;
    }*/

    /*public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setMaxAge(86400);
        return cookie;
    }

    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }*/


