package com.sparta.serviceteam4444.repository.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    Long countAllBySessionId(String sessionId);
}
