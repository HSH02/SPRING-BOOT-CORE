package com.example.springcore.di.Primary.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.springcore.di.Primary") // di 패키지를 스캔하여 Bean 등록
public class AppConfig {
}
