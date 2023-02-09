package com.sparta.serviceteam4444.dto;

import com.sparta.serviceteam4444.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatRoom {
    private String roomId;
    private String category;
    private String roomName;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String category, String roomName) {
        this.roomId = roomId;
        this.category = category;
        this.roomName = roomName;
    }
    //분기처리해주는 메소드
    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        //보내는 메세지가 ENTER인 경우 세션 추가해주기
        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }//추가된 상태에서 메시지 도착하면 메세지 띄워주기
        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}
