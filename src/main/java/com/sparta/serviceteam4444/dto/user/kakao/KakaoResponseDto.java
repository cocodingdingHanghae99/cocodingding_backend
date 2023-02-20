package com.sparta.serviceteam4444.dto.user.kakao;

import lombok.Getter;

@Getter
public class KakaoResponseDto {

    private String userEmail;

    private String userNickname;

    public KakaoResponseDto(KakaoUserInfoDto kakaoUserInfoDto) {
        this.userEmail = kakaoUserInfoDto.getUserEmail();
        this.userNickname = kakaoUserInfoDto.getUserNickname();
    }
}
