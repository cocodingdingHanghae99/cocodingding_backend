package com.sparta.serviceteam4444.social.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.serviceteam4444.jwt.JwtUtil;
import com.sparta.serviceteam4444.social.dto.MemberInfoDto;
import com.sparta.serviceteam4444.social.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class MemberController {
    private MemberService memberService;

    // 프론트에서 인가코드 받아오는 url
    @GetMapping("/kakao")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        String createToken = memberService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "성공";
    }
}
