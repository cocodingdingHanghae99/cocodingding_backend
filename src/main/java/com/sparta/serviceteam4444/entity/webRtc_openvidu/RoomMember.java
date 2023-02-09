package com.sparta.serviceteam4444.entity.webRtc_openvidu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    //체팅방
    private String sessionId;
    //userEntityId
    private Long userId;
    private String nickname;
    //session 토큰
    private String enterRoomToken;
}
