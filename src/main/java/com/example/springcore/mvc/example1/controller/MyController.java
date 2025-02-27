package com.example.springcore.mvc.example1.controller;

import com.example.springcore.mvc.example1.service.MyService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class MyController {

    // Service 의존성 주입
    private final MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
        System.out.println("[MyController] Constructor 호출: MyController 빈이 생성되었습니다.");
    }

    // @PostConstruct 메서드에서 로그 출력
    @PostConstruct
    public void init() {
        System.out.println("[MyController] @PostConstruct 호출: MyController 빈 초기화 완료.");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("[MyController] @PreDestroy 호출 : 빈 소멸");
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("[MyController] /test 요청 처리 시작");
        String result = myService.process();
        System.out.println("[MyController] /test 요청 처리 완료");
        return "Result: " + result;
    }
}