package com.sparta.serviceteam4444.dto.user;

import lombok.Getter;

@Getter
public class RefreshTokenDto {

    private final String userEmail;

    private final String token;

    private final String refreshToken;

    public RefreshTokenDto(String userEmail, String token, String refreshToken) {
        this.userEmail = userEmail;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
