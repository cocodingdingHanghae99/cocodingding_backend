package com.sparta.serviceteam4444.controller.webRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateRequestDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateResponseDto;
import com.sparta.serviceteam4444.security.user.UserDetailsImpl;
import com.sparta.serviceteam4444.service.wedRtc_openvidu.WedRtcService;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class WedRtcController {

    private final WedRtcService wedRtcService;
    //방 생성
    @PostMapping("/rooms")
    @ApiOperation(value = "방 생성 매소드")
    public RoomCreateResponseDto createRoom(@RequestBody RoomCreateRequestDto roomCreateRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails)throws OpenViduJavaClientException,
            OpenViduHttpException {
        return wedRtcService.createRoom(roomCreateRequestDto, userDetails);
    }
    //
}
