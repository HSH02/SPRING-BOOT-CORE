package com.example.springcore.aop.example6;

import org.springframework.stereotype.Service;

@Trackable
@Service
public class OtherService {
    public void baseMethod() {
        System.out.println("work");
    }
}
