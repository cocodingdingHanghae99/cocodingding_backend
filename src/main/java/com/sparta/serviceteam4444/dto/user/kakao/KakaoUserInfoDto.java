package com.sparta.serviceteam4444.dto.user.kakao;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {

    private String userNickname;

    private String email;

    public KakaoUserInfoDto(String nickname) {
        this.userNickname = nickname;
    }
}
