package com.sparta.serviceteam4444.social.controller;

import com.sparta.serviceteam4444.social.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class MemberController {
    private MemberService memberService;

    // 프론트에서 인가코드 받아오는 url
    @GetMapping("/kakao")
    public String kakaoCallback(String code){
        return code;
    }
}
