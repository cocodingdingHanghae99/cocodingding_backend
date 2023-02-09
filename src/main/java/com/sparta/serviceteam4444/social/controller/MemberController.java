package com.sparta.serviceteam4444.social.controller;

import com.sparta.serviceteam4444.social.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class MemberController {
    private MemberService memberService;

    // 프론트에서 인가코드 받아오는 url
    @GetMapping("/kakao")
    public String login(@RequestParam(value = "code", required = false) String code) throws Exception{
        System.out.println("code = " + code);
        String access_Token = memberService.getAccessToken(code);
        System.out.println("access_Token = " + access_Token);
        return memberService.getAccessToken(code);
    }
}
