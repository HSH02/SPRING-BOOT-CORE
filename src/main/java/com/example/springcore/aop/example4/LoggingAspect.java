package com.example.springcore.aop.example4;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // 1. execution: 특정 메서드 실행을 타겟팅 (메서드의 반환 타입, 이름, 인자 등을 지정)
    @Pointcut("execution(* com.example.springcore.aop.example4.DemoService.successfulMethod(..))")
    public void executionSuccessfulMethod() {
    }

    // 2. within: 특정 클래스(또는 패키지) 내부의 모든 조인 포인트를 타겟팅
    @Pointcut("within(com.example.springcore.aop.example4.DemoService)")
    public void withinDemoService() {
    }

    // 3. this: 프록시 객체의 타입을 기준으로 타겟팅
    @Pointcut("this(com.example.springcore.aop.example4.DemoService)")
    public void thisDemoService() {
    }

    // 4. target: 실제 대상 객체의 타입을 기준으로 타겟팅
    @Pointcut("target(com.example.springcore.aop.example4.DemoService)")
    public void targetDemoService() {
    }

    // 5. args: 메서드 인자의 타입을 기준으로 타겟팅 (여기서는 String 타입 인자)
    @Pointcut("args(java.lang.String)")
    public void argsString() {
    }

    // 6. @annotation: 메서드에 지정된 사용자 정의 어노테이션(Trackable)을 기준으로 타겟팅
    @Pointcut("@annotation(trackable)")
    public void annotationTrackable(Trackable trackable) {
    }

    // 7. @within: 클래스에 지정된 어노테이션을 기준으로 그 클래스 내부의 모든 조인 포인트 타겟팅
    @Pointcut("@within(com.example.springcore.aop.example4.Trackable)")
    public void withinTrackable() {
    }

    // 8. @target: 실제 대상 객체의 클래스에 지정된 어노테이션을 기준으로 타겟팅
    @Pointcut("@target(com.example.springcore.aop.example4.Trackable)")
    public void targetTrackable() {
    }

    // 9. @args: 메서드 인자의 어노테이션 정보를 기준으로 타겟팅 (여기서는 예시로 String 타입을 사용)
    @Pointcut("@args(com.example.springcore.aop.example4.Trackable)")
    public void argsAnnotation() {
    }

    // Around advice 예시: execution 포인트컷을 사용하여 성공적인 메서드 호출을 감싸고 진행 시간 로깅
    @Around("executionSuccessfulMethod()")
    public Object aroundExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[Around - execution] 시작: {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        log.info("[Around - execution] 종료: {}", joinPoint.getSignature().getName());
        return result;
    }

    // Before advice 예시: within 포인트컷을 사용하여 DemoService 내부의 모든 메서드 호출 전에 실행
    @Before("withinDemoService()")
    public void beforeWithin(JoinPoint joinPoint) {
        log.info("[Before - within] 대상 클래스: {}", joinPoint.getSignature().getDeclaringTypeName());
    }

    // After advice 예시: this 포인트컷을 사용하여 프록시 객체 정보 출력
    @After("thisDemoService()")
    public void afterThis(JoinPoint joinPoint) {
        log.info("[After - this] 프록시 객체: {}", joinPoint.getThis());
    }

    // AfterReturning advice 예시: target 포인트컷을 사용하여 실제 대상 객체 정보 출력
    @AfterReturning("targetDemoService()")
    public void afterReturningTarget(JoinPoint joinPoint) {
        log.info("[AfterReturning - target] 실제 대상 객체: {}", joinPoint.getTarget());
    }

    // Before advice 예시: args 포인트컷을 사용하여 인자 타입 확인 (여기서는 String)
    @Before("argsString()")
    public void beforeArgs(JoinPoint joinPoint) {
        log.info("[Before - args] 인자 목록: {}", Arrays.toString(joinPoint.getArgs()));
    }

    // Before advice 예시: @annotation 포인트컷을 사용하여 Trackable 어노테이션 값을 출력
    @Before("annotationTrackable(trackable)")
    public void beforeAnnotation(JoinPoint joinPoint, Trackable trackable) {
        log.info("[Before - @annotation] Trackable value: {}", trackable.value());
    }

    // Before advice 예시: @within 포인트컷을 사용하여 클래스에 Trackable 어노테이션이 적용된 경우의 메서드 출력
    @Before("withinTrackable()")
    public void beforeWithinAnnotation(JoinPoint joinPoint) {
        log.info("[Before - @within] 대상 메서드: {}", joinPoint.getSignature());
    }

    // Before advice 예시: @target 포인트컷을 사용하여 실제 대상 객체의 Trackable 어노테이션 정보를 출력
    @Before("targetTrackable()")
    public void beforeTargetAnnotation(JoinPoint joinPoint) {
        log.info("[Before - @target] 대상 객체: {}", joinPoint.getTarget());
    }

    // Before advice 예시: @args 포인트컷을 사용하여 인자에 적용된 어노테이션 정보를 출력
    @Before("argsAnnotation()")
    public void beforeArgsAnnotation(JoinPoint joinPoint) {
        log.info("[Before - @args] 인자 목록: {}", Arrays.toString(joinPoint.getArgs()));
    }
}
