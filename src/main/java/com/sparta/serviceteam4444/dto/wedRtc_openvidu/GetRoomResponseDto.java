package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import lombok.Getter;

@Getter
public class GetRoomResponseDto {
    private final String roomTitle;
    private final String sessionId;
    private final Long openviduRoomId;
    private final String category;
    private final Long currentMember;
    private final String masterUserNickname;
    private final String youtubeLink;

    public GetRoomResponseDto(Room room) {
        this.roomTitle = room.getRoomTitle();
        this.sessionId = room.getSessoinId();
        this.openviduRoomId = room.getOpenviduRoomId();
        this.category = room.getCategory();
        this.currentMember = room.getCurrentMember();
        this.masterUserNickname = room.getRoomMasterNickname();
        this.youtubeLink = room.getYoutubeLink();
    }

}
