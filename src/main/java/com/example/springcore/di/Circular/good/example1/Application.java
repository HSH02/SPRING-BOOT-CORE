package com.example.springcore.di.Circular.good.example1;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            A a = context.getBean(A.class);
            a.doSomething(); // A가 B를 호출하고, B가 A를 호출 (무한 루프 방지)

            System.out.println();

            B b = context.getBean(B.class);
            b.doSomething();
        }
    }
}
