package com.example.springcore.ioc.example.legacy;

import com.example.springcore.configuration.example.models.MyRepository;

public class LegacyMain {
    public static void main(String[] args) {
        // 개발자가 직접 의존 객체 생성
        MyRepository myRepository = new MyRepositoryImpl();

        // 서비스에 의존 객체를 수동 주입
        MyService myService = new MyService(myRepository);

        // 서비스 사용
        myService.performTask();
    }
}

class MyRepositoryImpl implements MyRepository {
    public String fetchData() {
        return "데이터 조회 결과";
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
