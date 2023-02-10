package com.sparta.serviceteam4444.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfoDto {
    private String nickname;
    private String email;

    public UserInfoDto(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}