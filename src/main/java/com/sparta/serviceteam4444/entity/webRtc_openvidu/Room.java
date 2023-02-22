package com.sparta.serviceteam4444.entity.webRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateSessionResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long openviduRoomId;
    @Column(nullable = false)
    private String roomTitle;
    @Column(nullable = false)
    private String sessoinId;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String roomMasterNickname;

    private Long currentMember;

    public Room(CreateSessionResponseDto newToken, RoomCreateRequestDto roomCreateRequestDto, String roomMasterNickname) {
        this.roomTitle = roomCreateRequestDto.getRoomTitle();
        this.sessoinId = newToken.getSessionId();
        this.category = roomCreateRequestDto.getCategory();
        this.roomMasterNickname = roomMasterNickname;
    }

    public void updateCRTMember(Long currentMember) {
        this.currentMember = currentMember;
    }
}
