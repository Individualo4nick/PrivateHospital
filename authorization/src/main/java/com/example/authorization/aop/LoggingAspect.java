package com.example.authorization.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Pointcut("within(com.example.authorization.controller.*)")
    public void isController(){}
    @Pointcut("within(com.example.authorization.service.*)")
    public void isService(){}
    @Pointcut("within(com.example.authorization.repo.*)")
    public void isRepo(){}

    @Before("isController()")
    public void controllerLoggingMethod(JoinPoint joinPoint){
        log.info("Invoked " + joinPoint.getSignature().getName() + " method in controller");
    }
    @Before("isService()")
    public void serviceLoggingMethod(JoinPoint joinPoint){
        log.info("Invoked " + joinPoint.getSignature().getName() + " method in service");
    }
    @Before("isRepo()")
    public void repoLoggingMethod(JoinPoint joinPoint){
        log.info("Invoked " + joinPoint.getSignature().getName() + " method in repo");
    }
}
