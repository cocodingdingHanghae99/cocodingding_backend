package com.sparta.serviceteam4444.hyunwu.controller;

import com.sparta.serviceteam4444.hyunwu.service.KakaoServiceTest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/test")
public class KakaoController {

    private KakaoServiceTest kakaoService;

    @PostMapping("/kakao")
    public String login(@RequestParam("code") String code){

        String access_Token = kakaoService.getKakaoAccessToken(code);
        System.out.println("access_Token = " + access_Token);

        return access_Token;
    }
}
