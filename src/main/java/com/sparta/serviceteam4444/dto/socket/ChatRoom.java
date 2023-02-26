package com.sparta.serviceteam4444.dto.socket;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private Long id;
    private String roomName;
    private String category;

    public static ChatRoom create(Long roomId, String roomName, String category) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.id = roomId;
        chatRoom.roomName = roomName;
        chatRoom.category = category;
        return chatRoom;
    }

}
