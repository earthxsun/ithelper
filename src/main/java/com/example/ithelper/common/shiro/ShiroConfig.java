package com.example.ithelper.common.shiro;

import com.example.ithelper.common.jwt.JWTFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    MyRealm myRealm(){
        return new MyRealm();
    }


    @Bean
    SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        LinkedHashMap<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt",new JWTFilter());
        shiroFilterFactoryBean.setFilters(filters);
        //shiroFilterFactoryBean.setLoginUrl("/login");
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        //shiroFilterFactoryBean.setUnauthorizedUrl("/notLogin");
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/test/**","anon");
        //filterChainDefinitionMap.put("/account/**","anon");
        //filterChainDefinitionMap.put("/logout","logout");
        filterChainDefinitionMap.put("/**","jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
