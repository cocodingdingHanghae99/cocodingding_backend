package com.sparta.serviceteam4444.entity.webRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateSessionResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateRequestDto;
import com.sparta.serviceteam4444.timestamp.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Room extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long openviduRoomId;
    @Column(nullable = false)
    private String roomTitle;
    @Column(nullable = false)
    private String sessoinId;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private Long roomMasterId;
    private boolean status;
    private String password;
    private Long currentMember;

    public Room(CreateSessionResponseDto newToken, RoomCreateRequestDto roomCreateRequestDto, Long roomMemberId) {
        this.roomTitle = roomCreateRequestDto.getRoomTitle();
        this.sessoinId = newToken.getSessionId();
        this.category = roomCreateRequestDto.getCategory();
        this.roomMasterId = roomMemberId;
        this.status = roomCreateRequestDto.isStatus();
        this.password = roomCreateRequestDto.getPassword();
    }

    public void updateCRTMember(Long currentMember) {
        this.currentMember = currentMember;
    }
}
