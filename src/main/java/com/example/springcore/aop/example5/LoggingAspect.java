package com.example.springcore.aop.example5;

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

    @Pointcut("@annotation(com.example.springcore.aop.example5.Trackable)")
    public void methodAnnotationPointcut() {
    }

    // @target 포인트컷: 대상 객체의 실제 클래스에 @Trackable이 있을 경우 매칭
    @Pointcut("@target(com.example.springcore.aop.example5.Trackable)")
    public void classAnnotationPointcut() {
    }

    @Before("methodAnnotationPointcut()")
    public void beforeMethodAnnotation(JoinPoint joinPoint) {
        log.info("[@annotation] 적용 - 메서드: {}", joinPoint.getSignature());
    }

    @Before("classAnnotationPointcut()")
    public void beforeClassAnnotation(JoinPoint joinPoint) {
        log.info("[@target] 적용 - 메서드: {}", joinPoint.getSignature());
    }
}
