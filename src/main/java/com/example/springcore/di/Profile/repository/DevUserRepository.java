package com.example.springcore.di.Profile.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev") // dev 환경에서 활성화
public class DevUserRepository implements UserRepository {
    @Override
    public String getUserName() {
        return "Developer User";
    }
}

