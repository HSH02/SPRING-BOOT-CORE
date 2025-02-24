package com.example.springcore.aop.example6;

import org.springframework.stereotype.Service;

@Trackable("BaseService")
@Service
public class BaseService {
    public void baseMethod() {
        System.out.println("BaseService.baseMethod 실행");
    }
}
