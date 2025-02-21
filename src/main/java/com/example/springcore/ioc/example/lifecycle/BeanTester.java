package com.example.springcore.ioc.example.lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BeanTester implements CommandLineRunner {

    @Autowired
    private MyService myService1;

    @Autowired
    private MyService myService2;

    @Override
    public void run(String... args) throws Exception {
        // 주입받은 Bean의 메서드 호출
        myService1.doTask();
        myService2.doTask();

        // 싱글톤 보장: 두 Bean의 해시코드를 비교하여 동일 인스턴스임을 확인
        System.out.println("myService1 hashCode: " + myService1.hashCode());
        System.out.println("myService2 hashCode: " + myService2.hashCode());
        System.out.println("동일 인스턴스 여부: " + (myService1 == myService2));
    }
}
