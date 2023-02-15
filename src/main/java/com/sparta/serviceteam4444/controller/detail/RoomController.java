package com.sparta.serviceteam4444.controller.detail;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateRoomRequestDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateRoomResponseDto;
import com.sparta.serviceteam4444.service.wedRtc_openvidu.RoomService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
@RequestMapping("/detail")
public class RoomController {

    private final RoomService roomService;

    //방 생성
    @PostMapping("/room")
    public CreateRoomResponseDto createRoom(@RequestBody CreateRoomRequestDto createRoomRequestDto,
                                            HttpServletRequest request) throws OpenViduJavaClientException, OpenViduHttpException{
//        return roomService.createRoom(createRoomRequestDto, request);
        return null;
    }
}
