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
    @Pointcut("bean(*Controller) || bean(*Service) || bean(*Repository) || bean(*Mapper)")
    public void loggingPointcut(){}

    @Before("loggingPointcut()")
    public void loggingMethod(JoinPoint joinPoint){
        log.info("Invoked " + joinPoint.getSignature().getName() + " method");
    }
}
