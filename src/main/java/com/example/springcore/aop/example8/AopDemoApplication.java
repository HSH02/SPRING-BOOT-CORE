package com.example.springcore.aop.example8;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example.springcore.aop.example8")
@EnableAspectJAutoProxy
public class AopDemoApplication {
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopDemoApplication.class)) {
            FirstService secondService = context.getBean(FirstService.class);
            secondService.performTask();
        }
    }
}
