package com.example.springcore.beanscope.example1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("singleton")
    public Foo fooSingleton() {
        return new Foo();
    }

    @Bean
    @Scope("prototype")
    public Foo fooPrototype() {
        return new Foo();
    }
}
