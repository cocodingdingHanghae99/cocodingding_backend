package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import io.openvidu.java.client.Connection;
import lombok.Getter;

@Getter
public class CreateSessionResponseDto {
    private final String sessionId;
    private final String token;
    private final String connectionId;

    public CreateSessionResponseDto(String sessionId, Connection connection) {
        this.sessionId = sessionId;
        this.token = connection.getToken();
        this.connectionId = connection.getConnectionId();
    }
}
