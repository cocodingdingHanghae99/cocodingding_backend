package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import lombok.Getter;

@Getter
public class GetRoomResponseDto {
    private String roomTitle;
    private String sessionId;
    private Long openviduRoomId;
    private String category;
    private Long currentMember;
    private final boolean isEmptyRoom;

    public GetRoomResponseDto(Room room, boolean isEmptyRoom) {
        this.roomTitle = room.getRoomTitle();
        this.sessionId = room.getSessoinId();
        this.openviduRoomId = room.getOpenviduRoomId();
        this.category = room.getCategory();
        this.currentMember = room.getCurrentMember();
        this.isEmptyRoom = isEmptyRoom;
    }

    public GetRoomResponseDto(boolean isEmptyRoom) {
        this.isEmptyRoom = isEmptyRoom;
    }
}
