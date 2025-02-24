package com.example.springcore.aop.example6;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class CompareAspect {

    // @within: 메서드가 선언된 클래스(즉, BaseService)에 @Trackable이 있으면 매칭
    @Pointcut("@within(com.example.springcore.aop.example6.Trackable)")
    public void withinPointcut() {}

    // @target: 런타임 대상 객체의 실제 타입(여기서는 SubService)이 @Trackable을 가지고 있어야 매칭
    @Pointcut("@target(com.example.springcore.aop.example6.Trackable)")
    public void targetPointcut() {}

    @Before("withinPointcut()")
    public void beforeWithin(JoinPoint joinPoint) {
        log.info("[@within] 적용 - 메서드: {}", joinPoint.getSignature());
    }

    @Before("targetPointcut()")
    public void beforeTarget(JoinPoint joinPoint) {
        log.info("[@target] 적용 - 메서드: {}", joinPoint.getSignature());
    }
}
