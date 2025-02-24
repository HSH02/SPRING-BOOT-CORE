package com.example.springcore.aop.example3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example.springcore.aop.example3")
@EnableAspectJAutoProxy
@Slf4j
public class AppDemoApplication {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppDemoApplication.class)) {
            DemoService demoService = context.getBean(DemoService.class);

            demoService.introduction("foo", 20);
        }
    }
}
