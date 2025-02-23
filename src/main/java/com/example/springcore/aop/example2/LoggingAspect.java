package com.example.springcore.aop.example2;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * 패키지 내의 모든 메서드 실행을 매칭합니다.
     */
    @Pointcut("execution(* com.example.springcore.aop.example2..*(..))")
    public void allMethods() {
    }

    /**
     * 메서드에 @Trackable 어노테이션이 붙은 경우를 매칭합니다.
     *
     * @param trackable trackable 어노테이션
     * @see Trackable
     */
    @Pointcut("@annotation(trackable)")
    public void trackableMethods(Trackable trackable) {
    }

    /**
     * 모든 메서드 실행 전
     *
     * @param joinPoint 조인 포인트
     */
    @Before("allMethods()")
    public void beforeAllMethods(JoinPoint joinPoint) {
        log.info("[Before] 메서드 진입 전 : {}", joinPoint.getSignature());
    }

    /**
     * 모든 메서드 실행 후
     *
     * @param joinPoint 조인 포인트
     */
    @After("allMethods()")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("[After] 메서드 진입 후 : {}", joinPoint.getSignature());
    }

    /**
     * 메서드가 정상적으로 반환된 후 호출
     *
     * @param joinPoint 조인 포인트
     */
    @AfterReturning(pointcut = "allMethods()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] 메서드 {} , 반환 값: {}", joinPoint.getSignature(), result);
    }

    /**
     * 메서드 실행 중 예외가 발생한 경우 호출
     *
     * @param joinPoint joinPoint
     * @param ex        예외
     */
    @AfterThrowing(pointcut = "allMethods()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
        log.info("[AfterThrowing] 메서드 {} , 예외 값 :", joinPoint.getSignature(), ex);
    }

    /**
     * 메서드 실행 전후를 감싸는 Advice
     *
     * @param pjp ProceedingJoinPoint
     * @return 실제 메서드
     * @throws Throwable 예외
     */
    @Around("allMethods()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        log.info("[Around] 진행하기 전 메서드 : {}", pjp.getSignature());
        Object result;

        try {
            result = pjp.proceed();
        } catch (Throwable t) {
            log.info("[Around] 잡은 예외:", t);
            throw t;
        }

        log.info("[Around] 진행하기 후 메서드 : {}", pjp.getSignature());
        return result;
    }

    @Before(value = "trackableMethods(trackable)", argNames = "jp,trackable")
    public void beforeTrackableMethod(JoinPoint jp, Trackable trackable) {
        log.info("[Before-Trackable] 메서드 {} 는 @Trackable가 붙어있고, 값은 : {} ",
                jp.getSignature(), trackable.value());
    }

}
