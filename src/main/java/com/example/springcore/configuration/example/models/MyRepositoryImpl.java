package com.example.springcore.configuration.example.models;

public class MyRepositoryImpl implements MyRepository {
    @Override
    public String fetchData() {
        return "데이터가 생성되었습니다.";
    }
}