package com.example.springcore.configuration.example2;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {

    @Bean
    public SimpleBean simpleBean() {
        return new SimpleBean();
    }

    /*@Bean
    public SimpleBeanConsumer simpleBeanConsumer() {
        // 에러 발생!
        return new SimpleBeanConsumer(simpleBean()); // 이 코드는 순수한 자바 메서드를 호출하는 것으로 작동하여 새 인스턴스를 반환한다. ➡️ 싱글톤 보장 실패
    }*/
}