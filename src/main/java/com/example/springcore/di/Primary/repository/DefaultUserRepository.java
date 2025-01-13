package com.example.springcore.di.Primary.repository;

import org.springframework.stereotype.Repository;

@Repository
public class DefaultUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Default User";
    }
}

