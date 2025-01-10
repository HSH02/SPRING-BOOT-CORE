package com.example.springcore.configuration.example;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyBean {

    @PostConstruct
    public void init() {
        log.info("MyBean init");
    }

    @PreDestroy
    public void cleanup() {
        log.info("MyBean cleanup");
    }
}
