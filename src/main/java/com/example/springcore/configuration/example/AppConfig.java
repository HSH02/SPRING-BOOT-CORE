package com.example.springcore.configuration.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RepositoryComponent.class, ProfileConfig.class})
@ComponentScan("com.example.springcore.configuration.example")
public class AppConfig {
}
