package com.sparta.serviceteam4444.social;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class KakaoController {

    private KakaoServiceTest kakaoServiceTest;

    @PostMapping("/kakao")
    public String login(@RequestParam("code") String code){
        String access_Token = kakaoServiceTest.getAccessToken(code);
        System.out.println("access_Token = " + access_Token);

        return access_Token;
    }
}
