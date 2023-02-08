package com.sparta.serviceteam4444.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    // 메시지 타입 : 입장, 채팅
    enum MessageType {
            ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String roomName; // 방이름
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
}
