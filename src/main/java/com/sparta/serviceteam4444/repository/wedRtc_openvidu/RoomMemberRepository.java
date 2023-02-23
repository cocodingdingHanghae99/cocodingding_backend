package com.sparta.serviceteam4444.repository.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    Long countAllBySessionId(String sessionId);
    List<RoomMember> findAllBySessionId(String sessionId);
    RoomMember findByUserNicknameAndSessionId(String userNickname, String sessionId);

    void deleteBySessionId(String sessionId);

    void deleteBySessionIdAndUserNickname(String sessionId, String userNickname);
}
