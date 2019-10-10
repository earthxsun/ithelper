package com.example.ithelper.common.aop;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.utils.UserTools;
import com.example.ithelper.system.dao.OperateLogRepository;
import com.example.ithelper.system.entity.OperateLog;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    OperateLogRepository operateLogRepository;

    @Pointcut("@annotation(com.example.ithelper.common.aop.OperationLog)")
    public void logPointCut() {
    }

    @After("logPointCut()")
    public void saveOperationLog(JoinPoint joinPoint) {

        OperateLog operateLog = new OperateLog();

        //保存操作人名字
        String currentUser = "";
        try {
            currentUser = UserTools.getCurrentUser();
        } catch (CommonException e) {
            e.printStackTrace();
        }
        operateLog.setUsername(currentUser);

        //保存操作方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperationLog operationLog = method.getAnnotation(OperationLog.class);
        operateLog.setOperation(operationLog.value());

        //保存操作内容
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            operateLog.setParams(StringUtils.join(args, ","));
        }
        operateLogRepository.save(operateLog);
    }
}
