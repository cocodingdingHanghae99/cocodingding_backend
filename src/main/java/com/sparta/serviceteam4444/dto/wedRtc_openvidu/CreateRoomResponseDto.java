package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CreateRoomResponseDto {
    private String sessionId;
    private String roomName;
    private String masterNickname;
    private List<RoomMemberResponseDto> roomMemberResponseDtoList; //맴버 리스트
    private String token;
    private Long currentMember;
    private String category;
}
