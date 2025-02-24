package com.example.springcore.aop.example4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DemoService {

    @Trackable("successfulMethod")
    public void successfulMethod(String msg) {
        log.info("메세지가 포함된 successfulMethod 내부: {}", msg);
    }

    @Trackable("methodWithReturn")
    public String methodWithReturn(@Trackable("asd") String name) {
        log.info("메세지가 포함된 methodWithReturn 내부: {}", name);
        return "Hello, " + name;
    }

    @Trackable("methodThatThrows")
    public void methodThatThrows() {
        log.info("methodThatThrows 내부, 예외 던짐");
        throw new RuntimeException("methodThatThrows에서 예외 발생");
    }

    public void selfInvocation() {
        log.info("selfInvocation 내부에서 successfulMethod를 호출합니다.");
        this.successfulMethod("Self call"); // 내부 호출
    }
}
