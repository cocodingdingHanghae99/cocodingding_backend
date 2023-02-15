package com.sparta.serviceteam4444.controller.detail;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.gson.Gson;

import com.sparta.serviceteam4444.service.wedRtc_openvidu.RoomService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import io.openvidu.java.client.Connection;
import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.SessionProperties;


@Api(tags = {"detail"})
@CrossOrigin(originPatterns = "*")
@RestController
public class TestController {

//    private final RoomService roomService;

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    private OpenVidu openvidu;

    @PostConstruct
    public void init() {
        this.openvidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    /**
     * @param params The Session properties
     * @return The Session ID
     */

    @PostMapping("/api/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties properties = SessionProperties.fromJson(params).build();
        Session session = openvidu.createSession(properties);
        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

    /**
     * @param sessionId The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     */

    @PostMapping("/api/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openvidu.getActiveSession(sessionId);
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();
        Connection connection = session.createConnection(properties);
        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

//    @PostMapping("/room")
//    public ResponseEntity<JsonObject> getToken(@RequestBody String sessionNameParam,
//                                               HttpSession httpSession) throws ParseException {
//        return roomService.getToken(sessionNameParam, httpSession);
//    }


    //방 생성
//    @PostMapping("/room")
//    @ApiOperation(value = "방 생성 메소드")
//    public CreateRoomResponseDto createRoom(@RequestBody CreateRoomRequestDto createRoomRequestDto,
//                                            HttpServletRequest request) throws OpenViduJavaClientException, OpenViduHttpException {
//        return roomService.createRoom(createRoomRequestDto, request);
//    }

//    방 접속
//    @PostMapping("/room/{roomId}")
//    @ApiOperation(value = "일반 멤버 방 접속 메소드")
//    public RoomMemberResponseDto enterRoom(@PathVariable String roomId,
//                                           HttpServletRequest request,
//                                           @RequestBody RoomPasswordRequestDto roomPasswordRequestDto)
//            throws OpenViduJavaClientException, OpenViduHttpException{
//        return roomService.enterRoom(roomId, request, roomPasswordRequestDto);
//    }
}
