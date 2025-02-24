package com.example.springcore.aop.example5;

import org.springframework.stereotype.Service;

@Service
@Trackable("ClassLevel")
public class ServiceB {

    public void methodInAnnotatedClass() {
        System.out.println("ServiceB.methodInAnnotatedClass 실행");
    }
}