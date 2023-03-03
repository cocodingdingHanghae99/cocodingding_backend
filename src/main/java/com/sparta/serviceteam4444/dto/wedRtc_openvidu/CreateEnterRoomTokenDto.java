package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import io.openvidu.java.client.Connection;
import lombok.Getter;

@Getter
public class CreateEnterRoomTokenDto {
    private final String newEnterRoomToken;
    private final String connectionId;

    public CreateEnterRoomTokenDto(Connection connection) {
        this.newEnterRoomToken = connection.getToken();
        this.connectionId = connection.getConnectionId();
    }
}
