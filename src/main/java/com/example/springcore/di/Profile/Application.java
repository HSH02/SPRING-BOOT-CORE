package com.example.springcore.di.Profile;


import com.example.springcore.di.Profile.config.AppConfig;
import com.example.springcore.di.Profile.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        // Environment 설정 (dev 또는 prod)
        System.setProperty("spring.profiles.active", "prod");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.getWelcomeMessage());

        context.close();
    }
}
