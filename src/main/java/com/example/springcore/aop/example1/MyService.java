package com.example.springcore.aop.example1;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public void performTask() {
        System.out.println("서비스 실행 중");
    }

    public void performTask2(String msg) {
        System.out.println(msg);
    }

}
