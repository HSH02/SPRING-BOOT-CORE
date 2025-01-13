package com.example.springcore.di.Conflict.good.example1.repository;

import org.springframework.stereotype.Component;

@Component("repository2")
public class UserRepositoryImpl2 implements UserRepository {
    @Override
    public String getUser() {
        return "User from Repository 2";
    }
}