package com.sparta.serviceteam4444.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    private String nickname;
    private String password;
}