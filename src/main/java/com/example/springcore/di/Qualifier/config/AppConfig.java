package com.example.springcore.di.Qualifier.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.springcore.di.Qualifier") // di 패키지를 스캔하여 Bean 등록
public class AppConfig {
}
