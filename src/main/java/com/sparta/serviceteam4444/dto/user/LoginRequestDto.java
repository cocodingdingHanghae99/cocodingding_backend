package com.sparta.serviceteam4444.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    private String nickname;
    private String password;
}