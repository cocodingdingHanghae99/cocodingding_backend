package com.sparta.serviceteam4444.dto.socket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String roomName;
    private String category;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoom create(ChatInfo chatInfo) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.id = UUID.randomUUID().toString();
        chatRoom.roomName = chatInfo.getRoomName();
        chatRoom.category = chatInfo.getCategory();
        return chatRoom;
    }

}
