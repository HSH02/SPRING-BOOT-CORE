package com.example.springcore.di.Conflict.good.example1.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("repository1")
@Primary
public class UserRepositoryImpl1 implements UserRepository {
    @Override
    public String getUser() {
        return "User from Repository 1";
    }
}