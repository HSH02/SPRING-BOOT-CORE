package com.example.springcore.aop.example5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example.springcore.aop.example5")
@EnableAspectJAutoProxy
@Slf4j
public class AopDemoApplication {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopDemoApplication.class)) {
            ServiceA serviceA = context.getBean(ServiceA.class);
            ServiceB serviceB = context.getBean(ServiceB.class);

            System.out.println("=== serviceA === 실행");
            serviceA.methodWithAnnotation();
            serviceA.methodWithoutAnnotation();

            System.out.println("=== serviceB === 실행");
            serviceB.methodInAnnotatedClass();
        }
    }
}
