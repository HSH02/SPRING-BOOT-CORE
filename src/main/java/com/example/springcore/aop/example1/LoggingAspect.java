package com.example.springcore.aop.example1;

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

    @Pointcut("execution(public * com.example.springcore.aop.example1.*.*(..))")
    public void serviceMethods() {}


    @Before("serviceMethods()")
    public void before(JoinPoint joinPoint) {
        log.info("메서드 실행 전: {}", joinPoint.getSignature().getName());

        // JoinPoint의 전체 문자열 표현
        log.info("JoinPoint toString: {}", joinPoint.toString());

        // JoinPoint의 간략한 문자열 표현
        log.info("JoinPoint toShortString: {}", joinPoint.toShortString());

        // JoinPoint의 상세 문자열 표현
        log.info("JoinPoint toLongString: {}", joinPoint.toLongString());

        // 프록시 객체 (this)와 실제 대상 객체 (target) 출력
        log.info("joinPoint.getThis(): {}", joinPoint.getThis());
        log.info("joinPoint.getTarget(): {}", joinPoint.getTarget());

        // 메서드 인수 출력 (배열을 문자열로 변환)
        log.info("메서드 인수: {}", Arrays.toString(joinPoint.getArgs()));

        // 메서드 시그니처 출력
        log.info("메서드 시그니처: {}", joinPoint.getSignature());

        // 소스 코드 위치 출력
        log.info("SourceLocation: {}", joinPoint.getSourceLocation());

        // 조인 포인트 종류 (예: "method-execution") 출력
        log.info("조인 포인트 종류: {}", joinPoint.getKind());

        // 정적 부분 출력 (불변 정보)
        log.info("정적 부분: {}", joinPoint.getStaticPart());
    }


    @After("serviceMethods()")
    public void after(JoinPoint joinPoint) {
        log.info("메서드 실행 후 : {}", joinPoint.getSignature().getName());
    }

    @Around("serviceMethods()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("메서드 실행 전 (Around) : {}", joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        log.info("ProceedingJoinPoint proceed: {}", joinPoint.proceed());
        log.info("ProceedingJoinPoint proceed: {}", joinPoint.proceed());
        log.info("메서드 실행 후` (Around) : {}", joinPoint.getSignature().getName());
        return result;
    }

}
