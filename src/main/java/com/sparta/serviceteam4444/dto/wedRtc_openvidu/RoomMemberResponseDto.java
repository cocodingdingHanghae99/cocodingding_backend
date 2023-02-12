package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomMemberResponseDto {
    //체팅방에 참여할때 받는 id
    private Long roomMemberId;
    //체팅방의 아이디
    private String sessionId;
    //맴버의 유저 id
    private Long userId;
    //맴버의 닉네임
    private String userNickname;
    //방장인지 확인 방장이면 true, 일반 맴버면 false
    private boolean roomMaster;
    //입장 토큰
    private String enterRoomToken;
    public RoomMemberResponseDto(RoomMember roomMember, boolean roomMaster) {
        this.roomMemberId = roomMember.getRoomMemberId();
        this.sessionId = roomMember.getSessionId();
        this.userId = roomMember.getUserId();
        this.userNickname = roomMember.getNickname();
        this.roomMaster = roomMaster;
        this.enterRoomToken = roomMember.getEnterRoomToken();
    }
}
