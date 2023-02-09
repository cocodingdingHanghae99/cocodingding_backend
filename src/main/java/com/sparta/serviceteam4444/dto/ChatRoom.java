package com.sparta.serviceteam4444.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
    private String roomId;
    private String roomName;
    private String category;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoom create(String roomName, String category) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomName = roomName;
        chatRoom.category = category;
        return chatRoom;
    }
}
