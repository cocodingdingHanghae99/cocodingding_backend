package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RoomResponseDto {
    private String roomTitle;
    private String sessionId;
    private String masterNickname;
    private Long currentUser;
    private Long maxUser;
}
