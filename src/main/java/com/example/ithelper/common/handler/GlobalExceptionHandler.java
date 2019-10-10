package com.example.ithelper.common.handler;

import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.common.response.CommonResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handleCommonException(CommonException ce){
        System.out.println("捕抓到CommonException");
        return new CommonResponse().code(ce.getCode()).message(ce.getMessage());
    }


    @ExceptionHandler(UnauthorizedException.class)
    public  CommonResponse handleAuthenticationException(UnauthorizedException ce){
        System.out.println("catch error:  UnauthorizedException");
        return new CommonResponse().message(ce.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResponse handleConstraintViolationException(ConstraintViolationException ce) {
        System.out.println("catch error:  ConstraintViolationException");
        ArrayList<String> list = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : ce.getConstraintViolations()) {
            list.add(constraintViolation.getMessageTemplate());
        }
        return new CommonResponse().message(StringUtils.join(list,",")).code(CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrCode());
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse handleException(Exception ce){
        System.out.println("-----------全局错误--------------");
        ce.printStackTrace();
        String msg = CommonErrorMsg.SYSTEM_ERROR.getErrMsg()+":" + ce.getClass();
        return new CommonResponse().message(msg).code(CommonErrorMsg.SYSTEM_ERROR.getErrCode()).data("没有数据");
    }
}
