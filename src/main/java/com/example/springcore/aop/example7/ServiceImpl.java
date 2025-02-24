package com.example.springcore.aop.example7;


import org.springframework.stereotype.Service;

@Service
@Trackable("ServiceImpl")  // 대상 객체에 어노테이션을 붙임 → @target 매칭에 사용됨
public class ServiceImpl implements Services {

    @Override
    public void performAction(ParameterWrapper param) {
        System.out.println("ServiceImpl.performAction 호출, 인자: " + param);
    }

}
