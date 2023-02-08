package com.sparta.serviceteam4444.controller;

import com.sparta.serviceteam4444.dto.ChatRoom;
import com.sparta.serviceteam4444.service.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = {"main"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/main")
public class ChatController {

    private final ChatService chatService;
    @ApiOperation(value = "채팅방 파기", notes = "방 하나를 추가한다.")
    @PostMapping("/rooms")
    public ChatRoom createRoom(@RequestParam String roomName, String category) {
        return chatService.createRoom(roomName, category);
    }

    @ApiOperation(value = "방 조회", notes = "방 목록을 조회한다.")
    @GetMapping("/rooms")
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}
