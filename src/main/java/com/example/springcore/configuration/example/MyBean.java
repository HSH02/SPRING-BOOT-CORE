package com.example.springcore.configuration.example;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

    @PostConstruct
    public void init() {
        System.out.println("[MyBean] Bean 생성 완료!");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("[MyBean] Bean 종료!");
    }
}
