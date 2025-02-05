package com.example.springcore.configuration.example;

import com.example.springcore.configuration.example.models.MyRepository;
import com.example.springcore.configuration.example.models.MyService;
import com.example.springcore.configuration.example.models.MyServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {

    @Bean
    @Profile("dev")
    public MyService devService(MyRepository myRepository) {
        System.out.println("[PROFILE] DEV 환경 활성화!");
        System.out.println("[PROFILE] DEV 메세지가 주입되었습니다!");
        return new MyServiceImpl(myRepository, "개발자 환경입니다!");
    }

    @Bean
    @Profile("prod")
    public MyService prodService(MyRepository myRepository) {
        System.out.println("[PROFILE] Prod 환경 활성화!");
        System.out.println("[PROFILE] Prod 메세지가 주입되었습니다!");
        return new MyServiceImpl(myRepository, "배포 환경입니다!");
    }

}
