package com.example.springcore.aop.example3;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public void introduction(String name, int age) {
        String message = String.format("Hello my name is %s,age is %d", name, age);
        System.out.println(message);
    }
}
