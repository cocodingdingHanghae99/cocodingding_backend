package com.sparta.serviceteam4444.dto.user;

import lombok.Getter;

@Getter
public class KakaoUserInfoDto {

    private Long id;

    private String userEmail;

    private String userNickname;

    public KakaoUserInfoDto(Long id, String userNickname, String userEmail) {
        this.id = id;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
    }
}
