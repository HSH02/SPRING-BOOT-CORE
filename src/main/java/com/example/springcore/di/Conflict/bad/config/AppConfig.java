package com.example.springcore.di.Conflict.bad.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.springcore.di.Conflict.bad")
public class AppConfig {
}
