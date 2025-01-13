package com.example.springcore.di.Profile.service;

import com.example.springcore.di.Profile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getWelcomeMessage() {
        return "Welcome, " + userRepository.getUserName();
    }
}
