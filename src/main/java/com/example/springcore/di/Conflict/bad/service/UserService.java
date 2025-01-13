package com.example.springcore.di.Conflict.bad.service;

import com.example.springcore.di.Conflict.bad.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    // Bean을 찾을 수 없음!
    // No qualifying bean of type 'UserRepository' available: expected single matching bean but found 2: repository1,repository2
    @Autowired
    public UserService(UserRepository userRepository) { // Bean 충돌 발생
        this.userRepository = userRepository;
    }

    public String getUserName() {
        return userRepository.getUser();
    }
}
