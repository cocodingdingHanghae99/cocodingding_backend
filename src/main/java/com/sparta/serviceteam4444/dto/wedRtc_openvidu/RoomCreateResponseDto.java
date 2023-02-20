package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import lombok.Getter;

@Getter
public class RoomCreateResponseDto {
    private String roomTitle;
    private String sessionId;
    private Long roomId;

    public RoomCreateResponseDto(Room room) {
        this.roomTitle = room.getRoomTitle();
        this.sessionId = room.getSessoinId();
        this.roomId = room.getRoomId();
    }
}
