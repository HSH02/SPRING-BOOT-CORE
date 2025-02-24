package com.example.springcore.aop.example5;

import org.springframework.stereotype.Service;

@Service
public class ServiceA {

    @Trackable("MethodLevel")
    public void methodWithAnnotation() {
        System.out.println("ServiceA.methodWithAnnotation 실행");
    }

    public void methodWithoutAnnotation() {
        System.out.println("ServiceA.methodWithoutAnnotation 실행");
    }
}