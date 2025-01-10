package com.example.springcore.configuration.example;

import com.example.springcore.configuration.example.models.MyRepository;
import com.example.springcore.configuration.example.models.MyService;
import com.example.springcore.configuration.example.models.MyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Bean
    @Profile("dev")
    public MyService devService(MyRepository myRepository) {
        return new MyServiceImpl(myRepository, "Dev Environment Message");
    }

    @Bean
    @Profile("prod")
    public MyService prodService(MyRepository myRepository) {
        return new MyServiceImpl(myRepository, "Prod Environment Message");
    }
    
}
