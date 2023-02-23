package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Getter;

@Getter
public class CreateSessionResponseDto {
    private final String sessionId;
    private final String token;
    private final String connectionId;

    public CreateSessionResponseDto(String sessionId, String token, String connectionId) {
        this.sessionId = sessionId;
        this.token = token;
        this.connectionId = connectionId;
    }
}
