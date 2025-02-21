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
        // 내부에서 SimpleBean()를 호출하지만,
        // CGLIB 프록시가 개입하여 이미 생성된 SimpleBean Bean을 반환합니다.
        @Bean
        public SimpleBean simpleBean() {
            return new SimpleBean();
        }
    }

    @Component
    static class ComponentConfig {
        // 프록시가 없으므로 매번 새로운 인스턴스가 생성됩니다
        @Bean
        public SimpleBean simpleBean() {
            return new SimpleBean();
        }
    }

    static class SimpleBean {

    }
}
