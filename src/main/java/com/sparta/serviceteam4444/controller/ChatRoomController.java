package com.sparta.serviceteam4444.controller;

import com.sparta.serviceteam4444.dto.ChatRoomRequestDto;
import com.sparta.serviceteam4444.dto.ChatRoomResponseDto;
import com.sparta.serviceteam4444.service.ChatRoomService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@Api(tags = {"main"})
@RequiredArgsConstructor
@RequestMapping("/main")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/rooms")
    public ChatRoomResponseDto createRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto, HttpServletRequest request) {
        return chatRoomService.createRoom(chatRoomRequestDto, request);
    }

    @GetMapping("/rooms") //전체조회
    public List<ChatRoomResponseDto> getRooms() {
        return chatRoomService.getRooms();
    }
}
