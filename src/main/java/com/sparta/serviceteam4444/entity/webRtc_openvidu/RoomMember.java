package com.sparta.serviceteam4444.entity.webRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateEnterRoomTokenDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class RoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomMemberId;
    @Column(nullable = false)
    private String userNickname;
    @Column(nullable = false)
    private boolean roomMaster;
    @Column(nullable = false)
    private String sessionId;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private String connectionId;

    public RoomMember(String userNickname, boolean roomMaster, String sessoinId, String token, String connectionId) {
        this.userNickname = userNickname;
        this.roomMaster = roomMaster;
        this.sessionId = sessoinId;
        this.token = token;
        this.connectionId = connectionId;
    }

    public RoomMember(String userNickname, boolean roomMaster, String sessoinId, CreateEnterRoomTokenDto newEnterRoomToken) {
        this.userNickname = userNickname;
        this.roomMaster = roomMaster;
        this.sessionId = sessoinId;
        this.token = newEnterRoomToken.getNewEnterRoomToken();
        this.connectionId = newEnterRoomToken.getConnectionId();
    }

    public void updateToken(String newEnterRoomToken) {
        this.token = newEnterRoomToken;
    }
}
