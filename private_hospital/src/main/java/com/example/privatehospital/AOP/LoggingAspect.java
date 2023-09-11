package com.example.privatehospital.AOP;

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
    @Pointcut("within(com.example.privatehospital.Controllers.*)")
    public void isController(){}
    @Pointcut("within(com.example.privatehospital.Services.*)")
    public void isService(){}
    @Pointcut("within(com.example.privatehospital.Repositories.*)")
    public void isRepository(){}

    @Before("isController()")
    public void controllerLoggingMethod(JoinPoint joinPoint){
        log.info("Invoked " + joinPoint.getSignature().getName() + " method in controller");
    }
    @Before("isService()")
    public void serviceLoggingMethod(JoinPoint joinPoint){
        log.info("Invoked " + joinPoint.getSignature().getName() + " method in service");
    }
    @Before("isRepository()")
    public void repositoryLoggingMethod(JoinPoint joinPoint){
        log.info("Invoked " + joinPoint.getSignature().getName() + " method in repository");
    }
}
