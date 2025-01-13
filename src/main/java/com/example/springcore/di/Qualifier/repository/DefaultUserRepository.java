package com.example.springcore.di.Qualifier.repository;

import org.springframework.stereotype.Repository;

@Repository("defaultUserRepository") // 특정 이름을 지정
public class DefaultUserRepository implements UserRepository {

    @Override
    public String getUserName() {
        return "Default User";
    }

}

