package com.example.springcore.aop.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppMain {
    public static void main(String[] args) {

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            MyService myService = context.getBean(MyService.class);
            myService.performTask();
        }
    }
}
