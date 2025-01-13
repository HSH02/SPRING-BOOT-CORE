package com.example.springcore.di.Injection.service;

import com.example.springcore.di.Injection.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // 1. 필드 주입
    @Autowired
    private UserRepository userRepository;

    // 2. 생성자 주입
    private final UserRepository constructorInjectedRepository;

    // 3. Setter 주입
    private UserRepository setterInjectedRepository;

    @Autowired
    public UserService(UserRepository constructorInjectedRepository) {
        this.constructorInjectedRepository = constructorInjectedRepository;
    }

    @Autowired
    public void setSetterInjectedRepository(UserRepository setterInjectedRepository) {
        this.setterInjectedRepository = setterInjectedRepository;
    }

    // 예제 메서드
    public String getWelcomeMessage() {
        return "Welcome, " + userRepository.getUserName(); // 필드 주입 사용
    }

    public String getConstructorWelcomeMessage() {
        return "Welcome, " + constructorInjectedRepository.getUserName(); // 생성자 주입 사용
    }

    public String getSetterWelcomeMessage() {
        return "Welcome, " + setterInjectedRepository.getUserName(); // Setter 주입 사용
    }
}
