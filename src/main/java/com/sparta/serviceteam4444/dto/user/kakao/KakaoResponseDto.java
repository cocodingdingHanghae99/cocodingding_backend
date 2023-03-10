package com.sparta.serviceteam4444.dto.user.kakao;

import lombok.Getter;

@Getter
public class KakaoResponseDto {

    private String userEmail;

    private String userNickname;

    private String token;

    public KakaoResponseDto(String accessToken, KakaoUserInfoDto kakaoUserInfoDto) {
        this.token = accessToken;
        this.userEmail = kakaoUserInfoDto.getEmail();
        this.userNickname = kakaoUserInfoDto.getUserNickname();
    }

}
