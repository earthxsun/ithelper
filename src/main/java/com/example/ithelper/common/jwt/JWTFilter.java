package com.example.ithelper.common.jwt;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.common.utils.SpringContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JWTFilter extends BasicHttpAuthenticationFilter {

    private int errorCode;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                System.out.println("error: isAccessAllowed");
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String token = req.getHeader("TOKEN");
        return token != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws CommonException {
        System.out.println("executeLogin()");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("TOKEN");
        String finger = httpServletRequest.getHeader("finger");
        JWTToken jwtToken = new JWTToken(token);
        String username = JWTUtil.getUsername(token);

        //获取stringRedisTemplate示例
        StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) SpringContextUtil.getBean("stringRedisTemplate");

        //从redis获取指纹
        String userFinger = stringRedisTemplate.opsForValue().get("itHelper." + username);

        if (finger.equals(userFinger)) {
            try {
                getSubject(request, response).login(jwtToken);
                return true;
            } catch (Exception e) {
                System.out.println("error: executeLogin");
                errorCode = CommonErrorMsg.USER_NOT_LOGIN.getErrCode();
                e.printStackTrace();
                return false;
            }
        } else {
            errorCode = CommonErrorMsg.USER_ALREADY_LOGIN.getErrCode();
            return false;
        }
    }

    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        System.out.println("---JWTFilter: sendChallenge---" + errorCode);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try {
            String msg = null;
            ObjectMapper mapper = new ObjectMapper();
            if (errorCode == CommonErrorMsg.USER_NOT_LOGIN.getErrCode()) {
                msg = mapper.writeValueAsString(new CommonResponse().message(CommonErrorMsg.USER_NOT_LOGIN.getErrMsg())
                        .code(CommonErrorMsg.USER_NOT_LOGIN.getErrCode()));
            }
            if (errorCode == CommonErrorMsg.USER_ALREADY_LOGIN.getErrCode()) {
                msg = mapper.writeValueAsString(new CommonResponse().message(CommonErrorMsg.USER_ALREADY_LOGIN.getErrMsg())
                        .code(CommonErrorMsg.USER_ALREADY_LOGIN.getErrCode()));
            }
            PrintWriter printWriter = httpServletResponse.getWriter();
            printWriter.print(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
