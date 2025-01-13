package com.example.springcore.di.Qualifier.repository;

import org.springframework.stereotype.Repository;

@Repository("customUserRepository") // 특정 이름을 지정
public class CustomUserRepository implements UserRepository {

    @Override
    public String getUserName() {
        return "Custom User";
    }

}