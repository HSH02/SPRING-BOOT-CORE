package com.example.springcore;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.SimpleThreadScope;

@Configuration
public class AppConfig {

    // "websocket" 스코프를 등록 (BeanFactoryPostProcessor를 통해)
    @Bean
    public static BeanFactoryPostProcessor scopeRegistrar() {
        return new BeanFactoryPostProcessor() {
            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
                beanFactory.registerScope("websocket", new SimpleThreadScope());
            }
        };
    }

    // 기본 singleton 스코프 (default)
    @Bean
    public ScopedBean singletonBean() {
        return new ScopedBean("singletonBean");
    }

    // 매번 새 인스턴스 생성하는 prototype 스코프
    @Bean
    @Scope("prototype")
    public ScopedBean prototypeBean() {
        return new ScopedBean("prototypeBean");
    }

    // HTTP 요청마다 새 인스턴스를 생성 (request 스코프)
    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ScopedBean requestBean() {
        return new ScopedBean("requestBean");
    }

    // HTTP 세션마다 새 인스턴스를 생성 (session 스코프)
    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ScopedBean sessionBean() {
        return new ScopedBean("sessionBean");
    }

    // 애플리케이션 전체에서 하나의 인스턴스 (application 스코프)
    @Bean
    @Scope(value = "application", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ScopedBean applicationBean() {
        return new ScopedBean("applicationBean");
    }

    // 웹소켓 세션마다 새 인스턴스를 생성 (websocket 스코프)
    @Bean
    @Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ScopedBean websocketBean() {
        return new ScopedBean("websocketBean");
    }
}
