package com.sparta.serviceteam4444.service.user.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.serviceteam4444.dto.user.kakao.KakaoResponseDto;
import com.sparta.serviceteam4444.dto.user.kakao.KakaoUserInfoDto;
import com.sparta.serviceteam4444.entity.user.User;
import com.sparta.serviceteam4444.jwt.JwtUtil;
import com.sparta.serviceteam4444.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoService {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @Value("${client_id}")
    private String client_id;

    @Value("${redirect_uri}")
    private String redirect_uri;

    @Value("${client_secret}")
    private String client_secret;



    public KakaoResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {

        String accessToken = getAccessToken(code);

        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(kakaoUserInfoDto.getEmail()));

        //로그인시 user 정보 저장하기.(예외를 처리하지 않고 optional로 받기)
        Optional<User> userDemo = userRepository.findByUserEmail(kakaoUserInfoDto.getEmail());
        //저장되어있는 user정보가 없다면 저장을 하자.
        if(userDemo.isEmpty()){
            User user = new User(kakaoUserInfoDto);
            userRepository.save(user);
        }
        return new KakaoResponseDto(accessToken, kakaoUserInfoDto);

    }

    private String getAccessToken(String code) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", client_id);
        body.add("redirect_uri", redirect_uri);
        body.add("client_secret", client_secret);
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
        log.info(nickname);
        String email = jsonNode.get("kakao_account").get("email").asText();

        return new KakaoUserInfoDto(nickname, email);

    }
}
