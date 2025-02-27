package com.example.springcore.mvc.example1.service;

import com.example.springcore.mvc.example1.repository.MyRepository;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    private final MyRepository myRepository;

    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public String process() {
        System.out.println("[MyService] 비즈니스 로직 실행");
        String data = myRepository.getData();
        System.out.println("[MyService] 데이터 처리 완료");
        return data;
    }
}