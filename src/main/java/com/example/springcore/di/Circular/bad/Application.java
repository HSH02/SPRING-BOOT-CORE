package com.example.springcore.di.Circular.bad;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            A a = context.getBean(A.class);
            a.doSomething(); // 순환 참조 테스트
            // 순환 참조 에러 발생
            // Annotation-specified bean name 'a' for bean class [com.example.springcore.di.Circular.good.example1.A] conflicts with existing,
            // non-compatible bean definition of same name and class [com.example.springcore.di.Circular.bad.A]
        }
    }
}
