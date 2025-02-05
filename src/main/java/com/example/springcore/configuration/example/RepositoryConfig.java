package com.example.springcore.configuration.example;

import com.example.springcore.configuration.example.models.MyRepository;
import com.example.springcore.configuration.example.models.MyRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RepositoryConfig {

    @Bean
    public MyRepository myRepository() {
        return new MyRepositoryImpl();
    }

}
