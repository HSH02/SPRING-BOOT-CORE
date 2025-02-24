package com.example.springcore.aop.example3;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogginAspect {

    @Pointcut("execution(public void com.example.springcore.aop.example3..*(String, int))")
    public void introduce() {
    }

    @Around("introduce()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long before = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        log.info("[Around] 진행하기 전 메서드: {}", joinPoint.getSignature().getName());
        log.info("[Around] 원래 인자들: {}", Arrays.toString(args));

        if (args != null && args.length > 0) {
            if (args[0] instanceof String) {
                args[0] = "Modified Foo";
            }
            if (args[1] instanceof Integer) {
                args[1] = (Integer) args[1] + 1;
            }
        }

        joinPoint.proceed(args);

        long after = System.currentTimeMillis();
        log.info("[Around] 진행한 후 메서드: {}", joinPoint.getSignature().getName());
        long elapsedMillis = after - before;
        double elapsedSeconds = elapsedMillis / 1000.0;
        log.info("[Around] 메서드 진행 시간 : {} seconds", elapsedSeconds);

        return joinPoint;
    }
}
