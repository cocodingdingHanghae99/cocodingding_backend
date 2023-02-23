package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Getter;

@Getter
public class CreateEnterRoomTokenDto {
    private String newEnterRoomToken;
    private String connectionId;

    public CreateEnterRoomTokenDto(String connectionId, String newEnterRoomToken) {
        this.newEnterRoomToken = newEnterRoomToken;
        this.connectionId = connectionId;
    }
}
