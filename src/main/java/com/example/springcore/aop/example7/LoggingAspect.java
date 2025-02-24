package com.example.springcore.aop.example7;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    // 1. @args: 메서드 호출 시 전달되는 인자 객체의 타입(실제 클래스)에 @Trackable 어노테이션이 붙어 있어야 매칭
    //    ParameterWrapper 클래스에 @Trackable가 붙었으므로 해당 인자를 전달하면 매칭됨
    @Before("@args(com.example.springcore.aop.example7.Trackable)")
    public void beforeArgs(JoinPoint joinPoint) {
        log.info("[@args] 인자 정보: {}", Arrays.toString(joinPoint.getArgs()));
    }

    // 2. @target: 런타임 대상 객체(실제 Bean의 클래스)에 @Trackable 어노테이션이 붙어 있는지 검사
    //    ServiceImpl은 @Trackable("ServiceImpl")이 붙어 있으므로 매칭됨
    @Before("@target(com.example.springcore.aop.example7.Trackable)")
    public void beforeTarget(JoinPoint joinPoint) {
        log.info("[@target] 대상 객체 타입: {}", joinPoint.getTarget().getClass().getName());
    }

    // 3. this: 프록시 객체의 타입 정보를 기준으로 매칭 (보통 인터페이스 타입을 사용)
    @Before("this(com.example.springcore.aop.example7.Services)")
    public void beforeThis(JoinPoint joinPoint) {
        log.info("[this] 프록시 객체 타입: {}", joinPoint.getThis().getClass().getName());
    }

    // 4. target: 실제 대상 객체의 타입 정보를 기준으로 매칭
    @Before("target(com.example.springcore.aop.example7.Services)")
    public void beforeTargetType(JoinPoint joinPoint) {
        log.info("[target] 실제 대상 객체 타입: {}", joinPoint.getTarget().getClass().getName());
    }
}
