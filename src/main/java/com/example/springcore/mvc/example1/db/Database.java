package com.example.springcore.mvc.example1.db;

import org.springframework.stereotype.Component;

@Component
public class Database {
    public String fetch() {
        System.out.println("[Database] 데이터 조회 수행");
        return "dummy-data";
    }
}