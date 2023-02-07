package com.sparta.serviceteam4444.nginx;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String success() {
        return "성공 테스트 하기";
    }

}