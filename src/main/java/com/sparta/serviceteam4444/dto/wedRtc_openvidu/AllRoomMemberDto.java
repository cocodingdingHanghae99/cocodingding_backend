package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import lombok.Getter;

import java.util.List;

@Getter
public class AllRoomMemberDto {
    private final List<String> roomMemberNicknameList;

    public AllRoomMemberDto(List<String> roomMemberNicknameList) {
        this.roomMemberNicknameList = roomMemberNicknameList;
    }
}
