package com.example.springcore.di.Conflict.good.example2.repository;

import org.springframework.stereotype.Component;

@Component("repository1")
public class UserRepositoryImpl1 implements UserRepository {
    @Override
    public String getUser() {
        return "User from Repository 1";
    }
}