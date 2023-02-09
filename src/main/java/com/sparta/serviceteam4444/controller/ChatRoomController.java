package com.sparta.serviceteam4444.controller;

import com.sparta.serviceteam4444.dto.ChatRoom;
import com.sparta.serviceteam4444.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("room")
    public String rooms(Model model) {
        return "chat/room";
    }

    @GetMapping("rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }

    @PostMapping("room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String roomName) {
        return chatRoomRepository.createChatRoom(roomName);
    }

    @GetMapping("room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }

    @GetMapping("room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}
