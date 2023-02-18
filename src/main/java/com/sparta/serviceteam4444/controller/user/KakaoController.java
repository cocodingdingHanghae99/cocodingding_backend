package com.sparta.serviceteam4444.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.serviceteam4444.dto.user.ResponseDto;
import com.sparta.serviceteam4444.dto.user.UserResponseDto;
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

    @GetMapping("/kakao")
    public ResponseDto<UserResponseDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        log.info("인가코드 받았나?");
        return kakaoService.kakaoLogin(code, response);

    }

}
