package com.sparta.serviceteam4444.service.room;

import com.sparta.serviceteam4444.dto.room.request.CreateRoomRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final
    public ResponseEntity<?> createRoom(CreateRoomRequestDto createRoomRequestDto, HttpServletRequest request) {
    }
}
