package com.sparta.serviceteam4444.nginx;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class TestController {
    @GetMapping("/")
    public String success() {

        return "성공ㅇ";
    }

}