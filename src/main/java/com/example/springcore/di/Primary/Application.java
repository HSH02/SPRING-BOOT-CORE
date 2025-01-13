package com.example.springcore.di.Primary;


import com.example.springcore.di.Primary.config.AppConfig;
import com.example.springcore.di.Primary.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.getWelcomeMessage());

        context.close();
    }
}
