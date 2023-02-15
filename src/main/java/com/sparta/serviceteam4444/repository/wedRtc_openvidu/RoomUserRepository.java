package com.sparta.serviceteam4444.repository.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomUserRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findAllBySessionId(String sessionId);

    Long countAllBySessionId(String sessionId);

    Optional<RoomMember> findBySessionIdAndUserNickname(String sessionId, String nickname);
}
