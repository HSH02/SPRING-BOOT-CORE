//package com.example.springcore.ioc.example.lifecycle;// MyService.java
//
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.PreDestroy;
//import org.springframework.stereotype.Service;
//
//@Service  // 이 어노테이션을 통해 MyService가 Bean으로 등록됨
//public class MyService {
//
//    public MyService() {
//        System.out.println("MyService 생성자 호출");
//    }
//
//    // Bean이 생성된 후 초기화 단계에서 실행되는 메서드
//    @PostConstruct
//    public void init() {
//        System.out.println("MyService 초기화 (PostConstruct)");
//    }
//
//    public void doTask() {
//        System.out.println("MyService의 작업 수행");
//    }
//
//    // 컨테이너 종료 시 호출되어 소멸 단계에서 실행되는 메서드
//    @PreDestroy
//    public void destroy() {
//        System.out.println("MyService 소멸 (PreDestroy)");
//    }
//}
