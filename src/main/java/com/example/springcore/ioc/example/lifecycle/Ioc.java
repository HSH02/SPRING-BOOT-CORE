package com.example.springcore.ioc.example.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
class MyService {

    public MyService() {
        System.out.println("MyService 생성자 호출");
    }

    @PostConstruct
    public void init() {
        System.out.println("MyService 초기화 (PostConstruct)");
    }

    public void doTask() {
        System.out.println("MyService의 작업 수행");
    }

    // 컨테이너 종료 시 호출되어 소멸 단계에서 실행되는 메서드
    @PreDestroy
    public void destroy() {
        System.out.println("MyService 소멸 (PreDestroy)");
    }
}

@Configuration
@ComponentScan(basePackages = "com.example.springcore.ioc.example.lifecycle")
class AppConfig {
    // AppConfig 내부에는 별도의 필드 없이 컴포넌트 스캔만 수행
}

public class Ioc {
    public static void main(String[] args) {
        // IoC 컨테이너 초기화
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            // 빈 가져오기: 같은 MyService Bean을 여러 번 요청하면 싱글톤이므로 동일 인스턴스가 반환됨
            MyService myService1 = context.getBean(MyService.class);
            MyService myService2 = context.getBean(MyService.class);

            myService1.doTask();
            myService2.doTask();

            // 싱글톤 보장: 두 Bean의 해시코드를 비교하여 동일 인스턴스임을 확인
            System.out.println("myService1 hashCode: " + myService1.hashCode());
            System.out.println("myService2 hashCode: " + myService2.hashCode());
            System.out.println("동일 인스턴스 여부: " + (myService1 == myService2));
        }
    }
}
