package com.example.springcore.aop.example4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example.springcore.aop.example4")
@EnableAspectJAutoProxy
@Slf4j
public class AopDemoApplication {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopDemoApplication.class)) {
            DemoService demoService = context.getBean(DemoService.class);

            log.info("==== successfulMethod 호출 ====");
            demoService.successfulMethod("Test Message");

            log.info("\n\n==== successfulMethod 호출 ====");
            String greeting = demoService.methodWithReturn("Alice");
            log.info("Greeting: {}", greeting);

            try {
                demoService.methodThatThrows();
            } catch (Exception e) {
                log.info("Exception: {}", e.getMessage());
            }

            log.info("\n\n==== selfInvocation  호출 ====");
            demoService.selfInvocation();
        }
    }
}
