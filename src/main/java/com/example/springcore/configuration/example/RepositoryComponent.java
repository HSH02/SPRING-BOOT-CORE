package com.example.springcore.configuration.example;

import com.example.springcore.configuration.example.models.MyRepository;
import com.example.springcore.configuration.example.models.MyRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RepositoryComponent {

    @Bean
    public MyRepository myRepository() {
        System.out.println("[REPOSITORY] MyRepository이 빈으로 등록되었습니다.");
        return new MyRepositoryImpl();
    }

}
