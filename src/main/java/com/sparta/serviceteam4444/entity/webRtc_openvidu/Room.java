package com.sparta.serviceteam4444.entity.webRtc_openvidu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    private String sessionId;   /* 방 번호
                                 Openvidu에서 발급된 해당 채팅방에 입장하기 위한 세션 (세션 == 채팅방)
                                 다른 유저들이 해당 채팅방에 입장 요청시 해당 컬럼을 사용하여 오픈비두에 다른 유저들의 채팅방 입장을 위한 토큰을 생성합니다.*/

    @Column(nullable = false)
    private String roomTitle;

    @Column
    private String masterUserNickname;

    @Column
    private Long currentMember;

    @OneToMany(mappedBy = "sessionId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomMember> roomMembers;

    public void updateCurrentMember(Long currentMember) {
        this.currentMember = currentMember;
    }

}
