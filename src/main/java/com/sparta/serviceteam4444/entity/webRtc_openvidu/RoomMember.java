package com.sparta.serviceteam4444.entity.webRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateEnterRoomTokenDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateSessionResponseDto;
import com.sparta.serviceteam4444.entity.user.User;
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
    @OneToOne
    @JoinColumn(name = "USER_Id")
    private User user;
    @Column(nullable = false)
    private boolean roomMaster;
    @Column(nullable = false)
    private String sessionId;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private String connectionId;

    public RoomMember(User user, boolean roomMaster, CreateSessionResponseDto newToken) {
        this.user = user;
        this.roomMaster = roomMaster;
        this.sessionId = newToken.getSessionId();
        this.token = newToken.getToken();
        this.connectionId = newToken.getConnectionId();
    }

    public RoomMember(User user, boolean roomMaster, CreateEnterRoomTokenDto newEnterRoomToken, String sessionId) {
        this.user = user;
        this.roomMaster = roomMaster;
        this.sessionId = sessionId;
        this.token = newEnterRoomToken.getNewEnterRoomToken();
        this.connectionId = newEnterRoomToken.getConnectionId();
    }

    public void updateToken(String newEnterRoomToken) {
        this.token = newEnterRoomToken;
    }
}
