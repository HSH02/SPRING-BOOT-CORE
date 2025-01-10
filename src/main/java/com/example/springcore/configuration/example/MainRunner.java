package com.example.springcore.configuration.example;

import com.example.springcore.configuration.example.models.MyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainRunner {
    public static void main(String[] args) {
        // 활성화할 Spring Profile 설정 (dev 또는 prod 중 선택)
        // spring.profiles.active를 dev로 설정하면 ProfileConfig에서 devService가 활성화됨
        System.setProperty("spring.profiles.active", "dev"); // "prod"로 변경하면 prodService가 활성화됨

        // AnnotationConfigApplicationContext를 사용해 Spring Context 초기화
        // AppConfig 클래스에서 Configuration 설정을 로드
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Spring Context에서 MyService Bean 가져오기
        // 활성화된 Profile에 따라 적합한 MyService Bean(devService 또는 prodService)이 주입됨
        MyService myService = context.getBean(MyService.class);

        // MyService의 작업 실행
        // 해당 서비스가 MyRepository에서 데이터를 가져와 출력
        myService.performTask();

        // Spring Context 종료
        context.close();
    }
}
