package com.sparta.serviceteam4444.controller.user;

import com.sparta.serviceteam4444.service.user.KakaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class KakaoController {

    private KakaoService kakaoService;

    @GetMapping(value = "/kakao")
    public String kakaoLogin(@RequestParam (value = "code", required = false) String code) throws Exception {

        System.out.println("#########" + code);

        String access_Token = kakaoService.getAccessToken(code);

        System.out.println("###access_Token#### : " + access_Token);

        return kakaoService.getAccessToken(code);

    }

}
