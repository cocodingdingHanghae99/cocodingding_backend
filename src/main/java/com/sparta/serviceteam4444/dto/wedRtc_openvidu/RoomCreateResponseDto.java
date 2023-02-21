package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import lombok.Getter;

@Getter
public class RoomCreateResponseDto {
    private String roomTitle;
    private String sessionId;
    private Long openviduRoomId;
    private String enterRoomToken;

    public RoomCreateResponseDto(Room room, String enterRoomToken) {
        this.roomTitle = room.getRoomTitle();
        this.sessionId = room.getSessoinId();
        this.openviduRoomId = room.getOpenviduRoomId();
        this.enterRoomToken = enterRoomToken;
    }
}
