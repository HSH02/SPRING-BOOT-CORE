package com.example.springcore.di.Qualifier;


import com.example.springcore.di.Qualifier.config.AppConfig;
import com.example.springcore.di.Qualifier.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.getWelcomeMessage());

        context.close();
    }
}
