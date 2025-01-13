package com.example.springcore.di.Circular.good.example1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class B {
    private final A a;

    @Autowired
    public B(@Lazy A a) {
        this.a = a;
    }

    public void doSomething() {
        System.out.println("B is doing something");
        // 조건을 추가하여 무한 호출을 방지
        if (a != null) {
            a.doSomethingFromB();
        }
    }

    public void doSomethingFromA() {
        System.out.println("B is responding to A");
    }
}