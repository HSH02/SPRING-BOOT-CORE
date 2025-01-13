package com.example.springcore.di.Primary.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary // 기본적으로 주입될 Bean
public class PrimaryUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Primary User";
    }
}