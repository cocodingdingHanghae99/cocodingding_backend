package com.sparta.serviceteam4444.repository.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.user.User;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    List<RoomMember> findAllBySessionId(String sessoinId);

    RoomMember findByUserAndSessionId(User user, String sessionId);

    void deleteBySessionId(String sessoinId);

    void deleteBySessionIdAndUser(String sessoinId, User user);

    Long countAllBySessionId(String sessionId);
}
