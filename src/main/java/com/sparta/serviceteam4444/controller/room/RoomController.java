package com.sparta.serviceteam4444.controller.room;

import com.sparta.serviceteam4444.dto.room.request.CreateRoomRequestDto;
import com.sparta.serviceteam4444.dto.room.response.ResponseDto;
import com.sparta.serviceteam4444.exception.RestApiExceptionHandler;
import com.sparta.serviceteam4444.service.room.RoomService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/detail")
public class RoomController {

    private final RoomService roomService;

    //방 만들기
    @PostMapping("/room")
    @ApiOperation(value = "방 생성")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequestDto createRoomRequestDto,
                                                              HttpServletRequest request)throws OpenViduJavaClientException, OpenViduHttpException{
        return roomService.createRoom(createRoomRequestDto, request);
    }
}
