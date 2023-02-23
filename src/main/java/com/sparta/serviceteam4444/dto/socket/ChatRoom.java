package com.sparta.serviceteam4444.dto.socket;

import com.sparta.serviceteam4444.service.chat.ChatService;
import lombok.Builder;
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

    private String name;

//    public static ChatRoom create(String name){
//
//        ChatRoom chatRoom = new ChatRoom();
//
//        chatRoom.roomId = UUID.randomUUID().toString();
//
//        chatRoom.name = name;
//
//        return chatRoom;
//
//    }

    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService){

        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)){
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }

        sendMessage(chatMessage, chatService);

    }

    public <T>void sendMessage(T message, ChatService chatService){
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }

}
