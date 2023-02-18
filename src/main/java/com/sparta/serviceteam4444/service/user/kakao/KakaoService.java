package com.sparta.serviceteam4444.service.user.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.serviceteam4444.dto.user.kakao.KakaoResponseDto;
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

    public KakaoResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {

        log.info(code);

        String accessToken = getAccessToken(code);

        log.info(accessToken);

        return new KakaoResponseDto(accessToken);

    }

    private String getAccessToken(String code) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "306c476f21776ce73e2df07d1ca45995");
        body.add("redirect_uri", "http://localhost:3000/user/kakao");
        body.add("code", code);

        log.info(body.toString());

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        log.info(String.valueOf(response));
        log.info(String.valueOf(kakaoTokenRequest));

        String responseBody = response.getBody();
        log.info(responseBody);
        ObjectMapper objectMapper = new ObjectMapper();
        log.info(objectMapper.toString());
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        log.info(String.valueOf(jsonNode));
        return jsonNode.get("access_token").asText();

    }

}
