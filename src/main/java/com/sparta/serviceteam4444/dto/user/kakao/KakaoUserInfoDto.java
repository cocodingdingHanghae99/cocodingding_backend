package com.sparta.serviceteam4444.dto.user.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {

    private String userEmail;

    private String userNickname;

    public KakaoUserInfoDto(String nickname, String email) {
        this.userEmail = email;
        this.userNickname = nickname;
    }
}
