package com.example.springcore.di.Circular.bad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class B {
    private final A a;

    @Autowired
    public B(A a) {
        this.a = a;
    }

    public void doSomething() {
        System.out.println("B is doing something");
        a.doSomething();
    }
}