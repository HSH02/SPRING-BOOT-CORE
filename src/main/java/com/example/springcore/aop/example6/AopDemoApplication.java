package com.example.springcore.aop.example6;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example.springcore.aop.example6")
@EnableAspectJAutoProxy
public class AopDemoApplication {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AopDemoApplication.class)) {
            SubService service = context.getBean(SubService.class);
            service.baseMethod();
            service.subMethod();

            OtherService otherService = context.getBean(OtherService.class);
            otherService.baseMethod();
        }
    }
}
