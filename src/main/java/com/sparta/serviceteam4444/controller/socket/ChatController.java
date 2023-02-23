package com.sparta.serviceteam4444.controller.socket;

import com.sparta.serviceteam4444.dto.socket.ChatMessage;
import com.sparta.serviceteam4444.dto.socket.ChatRoom;
import com.sparta.serviceteam4444.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(@RequestParam String name){
        return chatService.createRoom(name);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }

//    private final SimpMessagingTemplate messagingTemplate;
//
//    @MessageMapping("/chat")
//    public void message(ChatMessage message){
//        messagingTemplate.convertAndSend("/sub/chat/room" + message.getRoomId(), message);
//    }

}