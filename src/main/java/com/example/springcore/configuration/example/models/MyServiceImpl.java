package com.example.springcore.configuration.example.models;

public class MyServiceImpl implements MyService {
    private final MyRepository myRepository;
    private final String environmentMessage;

    public MyServiceImpl(MyRepository myRepository, String environmentMessage) {
        this.myRepository = myRepository;
        this.environmentMessage = environmentMessage;
    }

    @Override
    public void performTask() {
        System.out.println("[Service] 작업 수행: " + myRepository.fetchData());
        System.out.println("[Service] 환경 메세지: " + environmentMessage);
    }
}