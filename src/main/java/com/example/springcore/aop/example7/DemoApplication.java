package com.example.springcore.aop.example7;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example.springcore.aop.example7")
@EnableAspectJAutoProxy
public class DemoApplication {
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(DemoApplication.class)) {

            Services service = context.getBean(Services.class);
            ParameterWrapper param = new ParameterWrapper("TestData");
            service.performAction(param);
        }
    }
}
