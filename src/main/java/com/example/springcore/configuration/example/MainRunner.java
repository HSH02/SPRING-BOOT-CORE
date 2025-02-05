package com.example.springcore.configuration.example;

import com.example.springcore.configuration.example.models.MyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainRunner {
    public static void main(String[] args) {
        // 활성화할 Spring Profile 설정 (dev 또는 prod)
        System.setProperty("spring.profiles.active", "dev"); // "prod"로 변경하면 prodService가 활성화됨

        // 🌟 Step 1: Spring Context 초기화 (AppConfig 로드)
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // 🌟 Step 2: MyService Bean 가져오기
            MyService myService = context.getBean(MyService.class);
            // 🌟 Step 3: 서비스 실행 (MyService의 performTask 호출)
            myService.performTask();
        }  // 🌟 Step 4: try가 닫히면서 Spring Context 종료 (Bean 소멸 처리)

    }
}
