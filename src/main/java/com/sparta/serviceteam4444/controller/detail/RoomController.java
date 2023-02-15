package com.sparta.serviceteam4444.controller.detail;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateRoomRequestDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateRoomResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.EnterRoomResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomMemberResponseDto;
import com.sparta.serviceteam4444.security.user.UserDetailsImpl;
import com.sparta.serviceteam4444.service.wedRtc_openvidu.RoomService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
@RequestMapping("/detail")
@Api(tags = {"deatil"})
public class RoomController {

    private final RoomService roomService;

    //방 생성
    @ApiOperation(value = "화상채팅방 생성")
    @PostMapping("/room")
    public CreateRoomResponseDto createRoom(@RequestBody CreateRoomRequestDto createRoomRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws OpenViduJavaClientException, OpenViduHttpException{
        return roomService.createRoom(createRoomRequestDto, userDetails.getUser());
    }

    //방 접속
    @ApiOperation(value = "화상채팅방 접속")
    @PostMapping("/room/{roomId}")
    public RoomMemberResponseDto enterRoom(@PathVariable String roomId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws OpenViduJavaClientException, OpenViduHttpException{
        return roomService.enterRoom(roomId, userDetails.getUser());
    }
}
