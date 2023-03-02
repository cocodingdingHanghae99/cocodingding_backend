package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Getter;

@Getter
public class RoomCreateRequestDto {
    private String roomTitle;
    private String category;
    private boolean status;
    private String password;
}
