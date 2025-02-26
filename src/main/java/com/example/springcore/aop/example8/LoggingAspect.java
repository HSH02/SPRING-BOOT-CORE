package com.example.springcore.aop.example8;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("this(com.example.springcore.aop.example8.FirstService)")
    public void serviceMethods() {
    }


    @Before("serviceMethods()")
    public void before(JoinPoint joinPoint) {
        log.info("[Before]");
    }


    @After("serviceMethods()")
    public void after(JoinPoint joinPoint) {
        log.info("[After]");
    }

    @Around("serviceMethods()")
    public void aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[Around]");
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        joinPoint.proceed();
        long elapsedTime = endTime - startTime;
        log.info("[Around] Elapsed time: {} ms", elapsedTime);
    }

}
