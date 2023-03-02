package com.sparta.serviceteam4444.controller.detail;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.AllRoomMemberDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.GetRoomResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateRequestDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateResponseDto;
import com.sparta.serviceteam4444.security.user.UserDetailsImpl;
import com.sparta.serviceteam4444.service.wedRtc_openvidu.RoomService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
@RequestMapping("/detail")
@Api(tags = {"deatil"})
public class OpenviduController {

    private final RoomService roomService;

    //방 생성
    @ApiOperation(value = "화상채팅방 생성")
    @PostMapping("/room")
    public RoomCreateResponseDto createRoom(@RequestBody RoomCreateRequestDto roomCreateRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) throws OpenViduJavaClientException, OpenViduHttpException {
        return roomService.createRoom(roomCreateRequestDto, userDetails);
    }
    //방 입장 동시에 방 정보 return
    @ApiOperation(value = "방 입장")
    @PostMapping("/room/{roomId}")
    public RoomCreateResponseDto enterRoom(@PathVariable Long roomId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws OpenViduJavaClientException, OpenViduHttpException {
        return roomService.enterRoom(roomId, userDetails);
    }
    //방 전체 보여주기
    @ApiOperation(value = "방 전체 보여주기")
    @GetMapping("/room/{page}")
    public List<GetRoomResponseDto> getAllRooms(@PathVariable int page){
        return roomService.getAllRooms(page);
    }
    //방 나가기
    @ApiOperation(value = "방 나가기")
    @PostMapping("/room/exit/{roomId}")
    public String exitRoom(@PathVariable Long roomId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) throws OpenViduJavaClientException, OpenViduHttpException {
        return roomService.exitRoom(roomId, userDetails);
    }
    //방 안에 있는 사람의 닉네임 전부 가져오기
    @ApiOperation(value = "방 안에 있는 사람의 닉네임 전부 가져오기")
    @GetMapping("/roomMember/{roomId}")
    public AllRoomMemberDto getAllRoomMember(@PathVariable Long roomId){
        return roomService.getAllRoomMember(roomId);
    }
}