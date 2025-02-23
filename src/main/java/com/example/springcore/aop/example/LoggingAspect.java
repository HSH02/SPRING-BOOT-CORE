package com.example.springcore.aop.example;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * com.example.springcore.aop.example.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void before(JoinPoint joinPoint) {
        log.info("메서드 실행 전 : {}", joinPoint.getSignature().getName());
    }

    @After("serviceMethods()")
    public void after(JoinPoint joinPoint) {
        log.info("메서드 실행 후 : {}", joinPoint.getSignature().getName());
    }

    @Around("serviceMethods()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("메서드 실행 전 (Around) : {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        log.info("메서드 실행 후` (Around) : {}", joinPoint.getSignature().getName());
        return result;
    }

}
