package com.example.ithelper.common.shiro;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.jwt.JWTToken;
import com.example.ithelper.common.jwt.JWTUtil;
import com.example.ithelper.system.entity.User;
import com.example.ithelper.system.servie.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("MyRealm.doGetAuthorizationInfo()");
        String currentUser = JWTUtil.getUsername(SecurityUtils.getSubject().getPrincipal().toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        try {
            User user = userService.getUserByUsername(currentUser);
            roles.add(user.getRole().getRoleName());
            info.setRoles(roles);
            return info;
        } catch (CommonException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");

        try {
            String token = (String) authenticationToken.getCredentials();
            String username = JWTUtil.getUsername(token);
            User user = userService.getUserByUsername(username);

            if (!JWTUtil.verify(token, username, user.getPassword())) {
                System.out.println("token验证错误");
                throw new AuthenticationException("token无效");
            }
            return new SimpleAuthenticationInfo(token, token, getName());
        } catch (Exception e) {
            System.out.println("----------error:doGetAuthenticationInfo-------------");
            e.printStackTrace();
        }

        return null;
    }
}
