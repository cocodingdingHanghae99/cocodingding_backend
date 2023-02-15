package com.sparta.serviceteam4444.nginx;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class NginxTestController {
    @GetMapping("/")
    public String success() {

        return "성공이 왜 되는거지? 이것도 되려나";
    }

}