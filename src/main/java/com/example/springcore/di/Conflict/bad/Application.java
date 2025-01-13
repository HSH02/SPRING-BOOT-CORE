package com.example.springcore.di.Conflict.bad;


import com.example.springcore.di.Conflict.bad.config.AppConfig;
import com.example.springcore.di.Conflict.bad.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        // 특정 설정 클래스를 기반으로 IoC 컨테이너 초기화
        // No qualifying bean of type 'com.example.springcore.di.Conflict.bad.repository.UserRepository' available: expected single matching bean but found 2: repository1,repository2
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 특정 Bean 가져오기
        UserService userService = context.getBean(UserService.class);

        // Bean 사용
        System.out.println(userService.getUserName());

        // 컨텍스트 닫기
        context.close();
    }
}
