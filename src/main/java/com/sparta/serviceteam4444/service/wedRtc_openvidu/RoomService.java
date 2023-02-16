package com.sparta.serviceteam4444.service.wedRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.*;
import com.sparta.serviceteam4444.entity.user.User;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import com.sparta.serviceteam4444.exception.CheckApiException;
import com.sparta.serviceteam4444.exception.ErrorCode;
import com.sparta.serviceteam4444.repository.user.UserRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomUserRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomRepository;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomUserRepository roomUserRepository;

    private final UserRepository userRepository;

    private OpenVidu openVidu;

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    @PostConstruct
    public OpenVidu openVidu(){
        return this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    //방 생성
    public ResponseEntity<String> createRoom(Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException{

        SessionProperties properties = SessionProperties.fromJson(params).build();

        Session session = openVidu.createSession(properties);

        return new ResponseEntity<>(session.getSessionId(), HttpStatus.OK);
    }

//    public CreateRoomResponseDto createNewToken(User user) throws OpenViduJavaClientException, OpenViduHttpException{
//
//        String serverData = user.getNickname();
//
//        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
//                .type(ConnectionType.WEBRTC).data(serverData).build();
//
//        Session session = openVidu.createSession();
//
//        String token = session.createConnection(connectionProperties).getToken();
//
//        return CreateRoomResponseDto.builder()
//                .sessionId(session.getSessionId())
//                .token(token)
//                .build();
//    }

    //======================================================================================================//

    //방 접속
    public ResponseEntity<String> enterRoom(String sessionId, Map<String, Object> params) throws OpenViduJavaClientException, OpenViduHttpException {

        Session session = openVidu.getActiveSession(sessionId);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ConnectionProperties properties = ConnectionProperties.fromJson(params).build();

        Connection connection = session.createConnection(properties);

        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

//    private String enterRoomToken(User user, String sessionId) throws OpenViduHttpException, OpenViduJavaClientException {
//
//        String serverData = user.getNickname();
//
//        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
//                .type(ConnectionType.WEBRTC).data(serverData).build();
//
//        openVidu.fetch();
//
//        List<Session> activeSessionList = openVidu.getActiveSessions();
//
//        Session session = null; // 세션 초기화
//
//        for (Session getSession : activeSessionList) {
//            if (getSession.getSessionId().equals(sessionId)){
//                session = getSession;
//                break;
//            }
//        }
//
//        if (session == null){
//            throw new CheckApiException(ErrorCode.NOT_EXITS_ROOM);
//        }
//
//        return session.createConnection(connectionProperties).getToken();
//
//    }

    //======================================================================================================//

    //방 전체 조회
    public List<RoomResponseDto> getAllRooms() {
        List<Room> roomList = roomRepository.findAll();
        if (roomList.isEmpty()) {
            throw new CheckApiException(ErrorCode.NOT_EXITS_ROOM);
        }
        List<RoomResponseDto> createRoomResponseDtos = new ArrayList<>();
        for (Room room : roomList) {
            RoomResponseDto roomResponseDto = RoomResponseDto.builder()
                    .roomTitle(room.getRoomTitle())
                    .masterNickname(room.getMasterUserNickname())
                    .sessionId(room.getSessionId())
                    .currentUser(room.getCurrentMember())
                    .maxUser(room.getMaxUser())
                    .build();
            createRoomResponseDtos.add(roomResponseDto);
        }
        return createRoomResponseDtos;
    }

}
