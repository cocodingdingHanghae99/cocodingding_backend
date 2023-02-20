package com.sparta.serviceteam4444.entity.webRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateSessionResponseDto;
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

    public Room(CreateSessionResponseDto newToken, String roomTitle) {
        this.roomTitle = roomTitle;
        this.sessoinId = newToken.getSessionId();
    }
}
