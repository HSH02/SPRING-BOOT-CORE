package com.example.springcore.di.Circular.good.example2;

import org.springframework.stereotype.Component;

@Component
public class Mediator {
    public void mediate(String action) {
        System.out.println("Mediator is handling: " + action);
    }
}