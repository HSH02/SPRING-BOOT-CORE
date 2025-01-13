package com.example.springcore.di.Circular.good.example1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class A {
    private final B b;

    @Autowired
    public A(@Lazy B b) {
        this.b = b;
    }

    public void doSomething() {
        System.out.println("A is doing something");
        b.doSomethingFromA();
    }


    public void doSomethingFromB() {
        System.out.println("A is responding to B");
    }
}