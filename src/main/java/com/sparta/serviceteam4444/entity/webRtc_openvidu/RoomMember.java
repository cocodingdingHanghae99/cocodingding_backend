package com.sparta.serviceteam4444.entity.webRtc_openvidu;

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

    public RoomMember(String userNickname, boolean roomMaster, String sessionId, String token) {
        this.userNickname = userNickname;
        this.roomMaster = roomMaster;
        this.sessionId = sessionId;
        this.token = token;
    }
}
