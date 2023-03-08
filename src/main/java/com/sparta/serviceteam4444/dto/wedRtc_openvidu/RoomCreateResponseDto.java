package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import lombok.Getter;

@Getter
public class RoomCreateResponseDto {
    private final String roomTitle;
    private final String sessionId;
    private final Long openviduRoomId;
    private final String enterRoomToken;
    private final String roomMemberNickname;
    private final boolean roomMaster;
    private final Long currentMember;
    private final String youtubeLink;

    public RoomCreateResponseDto(Room room, RoomMember roomMember) {
        this.roomTitle = room.getRoomTitle();
        this.sessionId = room.getSessoinId();
        this.openviduRoomId = room.getOpenviduRoomId();
        this.enterRoomToken = roomMember.getToken();
        this.roomMemberNickname = roomMember.getUser().getUserNickname();
        this.roomMaster = roomMember.isRoomMaster();
        this.currentMember = room.getCurrentMember();
        this.youtubeLink = room.getYoutubeLink();
    }
}
