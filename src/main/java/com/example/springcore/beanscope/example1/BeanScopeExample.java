package com.example.springcore.beanscope.example1;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopeExample {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("[Singleton 스코프 Bean 테스트]");
        Foo singleton1 = context.getBean("fooSingleton", Foo.class);
        Foo singleton2 = context.getBean("fooSingleton", Foo.class);

        System.out.println("singleton1: " + singleton1);
        System.out.println("singleton2: " + singleton2);
        System.out.println("동일 객체인가? " + (singleton1 == singleton2));
        System.out.println();

        System.out.println("[Prototype 스코프 Bean 테스트]");
        Foo prototype1 = context.getBean("fooPrototype", Foo.class);
        Foo prototype2 = context.getBean("fooPrototype", Foo.class);

        System.out.println("prototype1: " + prototype1);
        System.out.println("prototype2: " + prototype2);
        System.out.println("동일 객체인가? " + (prototype1 == prototype2));

        // 컨텍스트 종료
        context.close();
    }
}
