package com.example.springcore.configuration.example2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class SpringTest {
    public static void main(String[] args) {
        System.out.println("==== @Configuration Test ====");
        AnnotationConfigApplicationContext configContext = new AnnotationConfigApplicationContext(Config.class);
        Config config = configContext.getBean(Config.class);
        singletonTest(config.simpleBean(), config.simpleBean(), configContext);
        System.out.println();

        System.out.println("==== @Component Test ====");
        AnnotationConfigApplicationContext componentContext = new AnnotationConfigApplicationContext(ComponentConfig.class);
        ComponentConfig componentConfig = componentContext.getBean(ComponentConfig.class);
        singletonTest(componentConfig.simpleBean(), componentConfig.simpleBean(), componentContext);

        /*
        결과 값
        ==== @Configuration Test ====
        Bean1: 1311146128
        Bean2: 1311146128
        Same instance? true

        ==== @Component Test ====
        Bean1: 694452085
        Bean2: 857068247
        Same instance? false
        */

    }

    private static void singletonTest(SimpleBean config, SimpleBean config1, AnnotationConfigApplicationContext configContext) {
        SimpleBean bean1 = config; // 내부 메서드 호출
        SimpleBean bean2 = config1; // 내부 메서드 호출

        System.out.println("Bean1: " + System.identityHashCode(bean1)); // 메모리 주소 출력
        System.out.println("Bean2: " + System.identityHashCode(bean2)); // 같은 주소일 경우 싱글톤 보장
        System.out.println("Same instance? " + (bean1 == bean2)); // true여야 함
        configContext.close();
    }

    @Configuration
    static class Config {
        @Bean
        public SimpleBean simpleBean() {
            return new SimpleBean();
        }
    }

    @Component
    static class ComponentConfig {
        @Bean
        public SimpleBean simpleBean() {
            return new SimpleBean();
        }
    }

    static class SimpleBean {
    }
}
