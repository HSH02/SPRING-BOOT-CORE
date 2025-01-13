package com.example.springcore.di.Circular.good.example2;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            A a = context.getBean(A.class);
            a.doSomething(); // A의 메서드 호출

            B b = context.getBean(B.class);
            b.doSomething(); // B의 메서드 호출
        }
    }
}
