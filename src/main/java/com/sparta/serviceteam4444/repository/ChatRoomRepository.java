package com.sparta.serviceteam4444.repository;


import com.sparta.serviceteam4444.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findAllByOrderByCreatedAtDesc();
}
