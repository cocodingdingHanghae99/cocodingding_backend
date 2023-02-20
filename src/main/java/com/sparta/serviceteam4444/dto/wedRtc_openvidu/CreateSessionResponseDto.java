package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateSessionResponseDto {
    private String sessionId;
    private String token;

    public CreateSessionResponseDto(String sessionId, String token) {
        this.sessionId = sessionId;
        this.token = token;
    }
}
