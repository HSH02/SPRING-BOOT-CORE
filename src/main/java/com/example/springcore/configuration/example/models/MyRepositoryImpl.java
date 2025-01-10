package com.example.springcore.configuration.example.models;

public class MyRepositoryImpl implements MyRepository {
    @Override
    public String fetchData() {
        return "샘플 데이터";
    }
}