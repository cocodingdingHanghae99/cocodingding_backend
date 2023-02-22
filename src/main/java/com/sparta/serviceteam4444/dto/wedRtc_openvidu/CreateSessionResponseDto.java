package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Getter;

@Getter
public class CreateSessionResponseDto {
    private final String sessionId;
    private final String token;

    public CreateSessionResponseDto(String sessionId, String token) {
        this.sessionId = sessionId;
        this.token = token;
    }
}
