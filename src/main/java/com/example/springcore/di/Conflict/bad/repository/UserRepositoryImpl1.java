package com.example.springcore.di.Conflict.bad.repository;

import org.springframework.stereotype.Component;

@Component("repository1")
public class UserRepositoryImpl1 implements UserRepository {
    @Override
    public String getUser() {
        return "User from Repository 1";
    }
}