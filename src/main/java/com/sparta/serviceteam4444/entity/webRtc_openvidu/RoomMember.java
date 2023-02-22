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

    public RoomMember(String userNickname, boolean roomMaster, String sessionId) {
        this.userNickname = userNickname;
        this.roomMaster = roomMaster;
        this.sessionId = sessionId;
    }
}
