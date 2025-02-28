package com.example.springcore.annotations.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class Controller {


    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public void getItems() {
        // 아이템 목록 반환
    }
}
