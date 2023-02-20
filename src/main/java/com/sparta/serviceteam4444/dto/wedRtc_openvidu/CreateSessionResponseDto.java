package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateSessionResponseDto {
    private String sessionId;
    private String token;
}
