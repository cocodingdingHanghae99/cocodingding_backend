package com.sparta.serviceteam4444.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.serviceteam4444.dto.ChatMessage;
import com.sparta.serviceteam4444.dto.ChatRoom;
import com.sparta.serviceteam4444.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component//여러 클라이언트가 발송한 메시지 처리해줄 Handler
public class WebSockChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        //메세지 뽑아내기
        String payload = message.getPayload();
        log.info("payload {}", payload);
        /* 채팅창에 출력하고 싶을 때
        TextMessage textMessage = new TextMessage("Welcome chatting sever~^^");
        session.sendMessage(textMessage);
         */

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomByRoomId(chatMessage.getRoomId());
        room.handleActions(session, chatMessage, chatService);
        log.info(chatMessage.getRoomId());
        log.info(chatMessage.getMessage());
    }
}
