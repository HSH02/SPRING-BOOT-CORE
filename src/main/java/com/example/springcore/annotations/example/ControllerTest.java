package com.example.springcore.annotations.example;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTest {


    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public void getItems() {
        // 아이템 목록 반환
    }
}
