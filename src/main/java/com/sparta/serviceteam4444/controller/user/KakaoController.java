package com.sparta.serviceteam4444.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.serviceteam4444.jwt.JwtUtil;
import com.sparta.serviceteam4444.service.user.KakaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping(value = "/user")
@CrossOrigin("http://localhost:3000")
public class KakaoController {

    private KakaoService kakaoService;

    @PostMapping("/kakao")
    public String kakaoLogin(@RequestBody String code, HttpServletResponse response) throws JsonProcessingException {
        //requestbody도 생각해볼 것

        return kakaoService.kakaoLogin(code, response);

    }

}
