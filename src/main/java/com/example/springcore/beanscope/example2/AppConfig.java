package com.example.springcore.beanscope.example2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class AppConfig {

    // 기본 singleton (default)
    @Bean
    public ScopedBean singletonBean() {
        return new ScopedBean("singletonBean");
    }

    // 매번 새 인스턴스 생성
    @Bean
    @Scope("prototype")
    public ScopedBean prototypeBean() {
        return new ScopedBean("prototypeBean");
    }

    // HTTP 요청마다 새 인스턴스 (웹 스코프)
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ScopedBean requestBean() {
        return new ScopedBean("requestBean");
    }

    // HTTP 세션마다 새 인스턴스 (웹 스코프)
    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ScopedBean sessionBean() {
        return new ScopedBean("sessionBean");
    }

    // ServletContext 기반, 애플리케이션 전체에서 하나의 인스턴스 (웹 스코프)
    @Bean
    @Scope(value = "application", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ScopedBean applicationBean() {
        return new ScopedBean("applicationBean");
    }

    // WebSocket 스코프 (웹 스코프)
    @Bean
    @Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ScopedBean websocketBean() {
        return new ScopedBean("websocketBean");
    }
}
