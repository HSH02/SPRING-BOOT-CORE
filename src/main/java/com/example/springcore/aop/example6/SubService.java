package com.example.springcore.aop.example6;

import org.springframework.stereotype.Service;

@Service
public class SubService extends BaseService {
    public void subMethod() {
        System.out.println("SubService.subMethod 실행");
    }
}
