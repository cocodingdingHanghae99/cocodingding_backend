package com.sparta.serviceteam4444.controller.user.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.serviceteam4444.dto.user.kakao.KakaoResponseDto;
import com.sparta.serviceteam4444.service.user.kakao.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class KakaoController {

    private final KakaoService kakaoService;

    @PostMapping("/user/kakao")
    public KakaoResponseDto kakaoLogin(@RequestBody String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code, response);
    }

}
