package com.sparta.serviceteam4444.controller.detail;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.*;
import com.sparta.serviceteam4444.security.user.UserDetailsImpl;
import com.sparta.serviceteam4444.service.wedRtc_openvidu.RoomService;
import io.openvidu.java.client.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<String> createRoom(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException{
        return roomService.createRoom(params);
    }

    //방 접속
    @ApiOperation(value = "화상채팅방 접속")
    @PostMapping("/room/{sessionId}")
    public ResponseEntity<String> enterRoom(@PathVariable("sessionId") String sessionId,
                                           @RequestBody(required = false) Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException{
        return roomService.enterRoom(sessionId, params);
    }

    //방 전체 목록 조회
    @GetMapping("/room")
    @ApiOperation(value = "session 조회")
    public List<RoomResponseDto> getAllRooms() {
        return roomService.getAllRooms();
    }
<<<<<<< HEAD:src/main/java/com/sparta/serviceteam4444/controller/detail/RoomController.java

    //방 정보 get요청
    @GetMapping("/room/{roomId}")
    @ApiOperation(value = "방 정보 get")
    public RoomResponseDto getRoom(@PathVariable String roomId){
        return roomService.getRoom(roomId);
    }
}
=======
}
>>>>>>> 75f202010a181ab20184b4504ed4a4e7d8e30e3f:src/main/java/com/sparta/serviceteam4444/controller/detail/OpenviduController.java
