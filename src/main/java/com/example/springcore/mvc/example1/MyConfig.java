package com.example.springcore.mvc.example1;

import com.example.springcore.mvc.example1.Interceptor.MyHandlerInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.example.springcore.mvc.example1")
@EnableWebMvc  // MVC 관련 설정 활성화 (Interceptor 등록 등)
class MyConfig implements WebMvcConfigurer {

    // 등록한 Interceptor를 Spring MVC에 추가
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(new MyHandlerInterceptor());
    }
}