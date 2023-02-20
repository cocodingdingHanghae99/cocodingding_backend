package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import lombok.Getter;

@Getter
public class GetRoomResponseDto {
    private String roomTitle;
    private String sessionId;
    private Long openviduRoomId;

    public GetRoomResponseDto(Room room) {
        this.roomTitle = room.getRoomTitle();
        this.sessionId = room.getSessoinId();
        this.openviduRoomId = room.getOpenviduRoomId();
    }
}
