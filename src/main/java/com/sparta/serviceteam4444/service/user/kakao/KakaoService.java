package com.sparta.serviceteam4444.service.user.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.serviceteam4444.dto.user.kakao.KakaoResponseDto;
import com.sparta.serviceteam4444.dto.user.kakao.KakaoUserInfoDto;
import com.sparta.serviceteam4444.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoService {

    private final JwtUtil jwtUtil;

    public KakaoResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {

        String accessToken = getAccessToken(code);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(accessToken));

        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);

        return new KakaoResponseDto(kakaoUserInfoDto);

    }

    private String getAccessToken(String code) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "306c476f21776ce73e2df07d1ca45995");
        body.add("redirect_uri", "http://localhost:3000/user/kakao");
        body.add("client_secret", "WeulIUQTCQSHS7yMTh7oVjelhXR5ZowN");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();

    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException{

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String nickname = jsonNode.get("properties")
                .get("nickname").asText();

        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        return new KakaoUserInfoDto(nickname, email);

    }

}
