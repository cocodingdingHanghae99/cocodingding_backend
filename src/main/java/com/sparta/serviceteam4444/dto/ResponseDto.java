package com.sparta.serviceteam4444.dto;

import lombok.Getter;

@Getter
public class ResponseDto {
    private String message;

    public ResponseDto(String message) {
        this.message = message;
    }
}
