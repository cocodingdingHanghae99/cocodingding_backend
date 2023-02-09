package com.sparta.serviceteam4444.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.serviceteam4444.dto.KakaoUserInfoDto;
import com.sparta.serviceteam4444.dto.ResponseDto;
import com.sparta.serviceteam4444.entity.User;
import com.sparta.serviceteam4444.jwt.JwtUtil;
import com.sparta.serviceteam4444.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public ResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code);

        // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);

        // 토큰 던져주기
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(kakaoUser.getNickname(), kakaoUser.getRole()));
        return new ResponseDto("카카오 로그인 완료");
    }
    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성 (상세히 적자면 코드 받은 값을 가지고 http 주소로 만들어줌)
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "dca78b23ee6bbb566b637457b88b9de0");
        body.add("redirect_uri", "http://localhost:3000/user/kakao");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        System.out.println("responseBody = " + responseBody);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }
    // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        //response 받은 것을 해체하기
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        log.info(jsonNode.get("kakao_account").get("email").asText())   ;
        log.info("코드 받음");
        //성분별로 쓰기 좋게 분리하기
        Long id = jsonNode.get("id").asLong();
        String nickName = jsonNode.get("properties").get("nickname").asText();
        String email;
        try {
            email = jsonNode.get("kakao_account").get("email").asText();
        }catch(NullPointerException e){
            email = "";
        }
        return new KakaoUserInfoDto(id, nickName, email);
    }
    // 3. 필요시에 회원가입
    private User registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        String email = kakaoUserInfo.getEmail();
        User kakaoUser;
        // 카카오 사용자 id 동일한 id 가진 회원이 있는지 확인
        User sameidUser = userRepository.findByKakaoId(kakaoId).orElse(null);
        if (sameidUser != null) {
            kakaoUser = sameidUser;
            // 기존 회원정보에 카카오 Id 추가
            kakaoUser = kakaoUser.kakaoIdUpdate(kakaoId);
        } else {
            // 신규 회원가입
            // password: random UUID 무작위 난수로 만들고 이걸 또 인코딩해서 버무린다.
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);
            //유저 틀 만들기
            kakaoUser = new User(kakaoUserInfo.getNickname(), email, kakaoId, encodedPassword);
        }
        //저장!
        userRepository.save(kakaoUser);
        return kakaoUser;
    }

}
