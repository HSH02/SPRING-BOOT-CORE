package com.example.springcore.ioc.example.di;

import com.example.springcore.configuration.example.models.MyRepository;
import com.example.springcore.configuration.example.models.MyRepositoryImpl;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = "com.example.springcore.ioc.example.di")
// @EnableLoadTimeWeaving
class AppConfig {
    // MyRepository 타입 Bean 등록
    @Bean
    public MyRepository myRepository() {
        return new MyRepositoryImpl();
    }

    // MyService 타입 Bean 등록
    @Bean
    public MyService myService(MyRepository myRepository) {
        return new MyService(myRepository);
    }
}

class MyService {
    private final MyRepository myRepository;

    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public void performTask() {
        System.out.println("[서비스] " + myRepository.fetchData());
    }
}


public class Ioc {
    public static void main(String[] args) {
        // IoC 컨테이너 초기화
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(AppConfig.class)) {
            // 빈 가져오기
            MyService service = context.getBean(MyService.class);
            // 서비스 로직 실행
            service.performTask();
        }
    }
}
