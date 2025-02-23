package com.example.springcore.aop.example;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public void performTask() {
        System.out.println("서비스 실행 중");
    }

}
