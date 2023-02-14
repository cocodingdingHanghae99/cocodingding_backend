package com.sparta.serviceteam4444.controller.detail;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateRoomRequestDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateRoomResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomMemberResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomPasswordRequestDto;
import com.sparta.serviceteam4444.service.wedRtc_openvidu.RoomService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("/detail")
public class RoomController {

    private final RoomService roomService;

    //방 생성
    @PostMapping("/room")
    @ApiOperation(value = "방 생성 메소드")
    public CreateRoomResponseDto createRoom(@RequestBody CreateRoomRequestDto createRoomRequestDto,
                                            HttpServletRequest request) throws OpenViduJavaClientException, OpenViduHttpException {
        return roomService.createRoom(createRoomRequestDto, request);
    }

//    방 접속
    @PostMapping("/room/{roomId}")
    @ApiOperation(value = "일반 멤버 방 접속 메소드")
    public RoomMemberResponseDto enterRoom(@PathVariable String roomId,
                                           HttpServletRequest request,
                                           @RequestBody RoomPasswordRequestDto roomPasswordRequestDto)
            throws OpenViduJavaClientException, OpenViduHttpException{
        return roomService.enterRoom(roomId, request, roomPasswordRequestDto);
    }
}
