package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Getter;

@Getter
public class ExitRoomDto {
    private boolean roomMaster;
    private Long openviduRoomId;
    private String sessionId;
}
