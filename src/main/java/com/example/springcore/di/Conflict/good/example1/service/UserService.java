package com.example.springcore.di.Conflict.good.example1.service;

import com.example.springcore.di.Conflict.good.example1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { // Bean 충돌 발생
        this.userRepository = userRepository;
    }

    public String getUserName() {
        return userRepository.getUser();
    }
}
