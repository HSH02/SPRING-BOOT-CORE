package com.example.springcore.di.Circular.good.example2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class B {
    private final Mediator mediator;

    @Autowired
    public B(Mediator mediator) {
        this.mediator = mediator;
    }

    public void doSomething() {
        System.out.println("B is doing something");
        mediator.mediate("Action from B");
    }
}