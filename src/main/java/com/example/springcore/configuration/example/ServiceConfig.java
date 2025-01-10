package com.example.springcore.configuration.example;

import com.example.springcore.configuration.example.models.MyService;
import com.example.springcore.configuration.example.models.MyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    private final RepositoryConfig repositoryConfig;

    public ServiceConfig(RepositoryConfig repositoryConfig) {
        this.repositoryConfig = repositoryConfig;
    }

    @Bean
    public MyService myService() {
        return new MyServiceImpl(repositoryConfig.myRepository(), "prod");
    }
}