package com.example.springcore.aop.example8;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SecondService implements FirstService {

    @Override
    public void performTask() {

        log.info("[SecondService]");

    }

}
