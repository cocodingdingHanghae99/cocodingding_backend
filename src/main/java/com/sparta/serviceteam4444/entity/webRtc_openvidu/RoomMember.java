package com.sparta.serviceteam4444.entity.webRtc_openvidu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomMember {
    //체팅방에 참여할때 받는 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomMemberId;

    // 채팅방
    @Column
    private String sessionId;

    @Column
    private String userEmail;

    //userEntityId
    @Column
    private Long userId;

    @Column
    private String userNickname;

    //session 토큰
    @Column
    private String enterRoomToken;
}
