package com.example.springcore.di.Injection.repository;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public String getUserName() {
        return "Hello USER Repository!";
    }
}