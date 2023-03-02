package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import lombok.Getter;

@Getter
public class RoomCreateResponseDto {
    private String roomTitle;
    private String sessionId;
    private Long openviduRoomId;
    private String enterRoomToken;
    private String roomMemberNickname;
    private boolean roomMaster;
    private Long currentMember;

    public RoomCreateResponseDto(Room room, RoomMember roomMember) {
        this.roomTitle = room.getRoomTitle();
        this.sessionId = room.getSessoinId();
        this.openviduRoomId = room.getOpenviduRoomId();
        this.enterRoomToken = roomMember.getToken();
        this.roomMemberNickname = roomMember.getUserNickname();
        this.roomMaster = roomMember.isRoomMaster();
        this.currentMember = room.getCurrentMember();
    }
}
