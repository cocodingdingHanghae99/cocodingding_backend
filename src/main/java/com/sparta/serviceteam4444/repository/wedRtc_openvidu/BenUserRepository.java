package com.sparta.serviceteam4444.repository.wedRtc_openvidu;

import com.sparta.serviceteam4444.entity.webRtc_openvidu.BenUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenUserRepository extends JpaRepository<BenUser, Long> {
    BenUser findByUserIdAndRoomId(Long userId, String roomId);
}
