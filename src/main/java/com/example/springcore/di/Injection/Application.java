package com.example.springcore.di.Injection;


import com.example.springcore.di.Injection.config.AppConfig;
import com.example.springcore.di.Injection.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        // IoC 컨테이너 초기화
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Bean 가져오기
        UserService userService = context.getBean(UserService.class);

        // 출력
        // 각각의 DI 방식 확인
        System.out.println(userService.getWelcomeMessage());              // 필드 주입
        System.out.println(userService.getConstructorWelcomeMessage());  // 생성자 주입
        System.out.println(userService.getSetterWelcomeMessage());       // Setter 주입

        // 컨테이너 종료
        context.close();
    }
}
