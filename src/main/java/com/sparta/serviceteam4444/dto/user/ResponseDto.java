package com.sparta.serviceteam4444.dto.user;

import lombok.Getter;

@Getter
public class ResponseDto {
    private String message;

    public ResponseDto(String message) {
        this.message = message;
    }
}
