package com.sparta.serviceteam4444.service.wedRtc_openvidu;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.*;
import com.sparta.serviceteam4444.entity.user.User;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.BenUser;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import com.sparta.serviceteam4444.exception.CheckApiException;
import com.sparta.serviceteam4444.exception.ErrorCode;
import com.sparta.serviceteam4444.repository.user.UserRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.BenUserRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomUserRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomRepository;
import com.sparta.serviceteam4444.security.user.UserDetailsImpl;
import com.sparta.serviceteam4444.security.user.UserDetailsServiceImpl;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
        return openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    //방 생성
    public CreateRoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto,
                                            User user) throws OpenViduJavaClientException, OpenViduHttpException{
        //session생성 및 token받아오기
        CreateRoomResponseDto newToken = createNewToken(user, createRoomRequestDto.getSessionId());

        System.out.println("newToken = " + newToken);
        //newToken바탕으로 Room build
        Room room = Room.builder()
                .sessionId(newToken.getSessionId())
                .masterUserNickname(user.getNickname())
                .build();

        RoomMember roomMember = RoomMember.builder()
                .sessionId(newToken.getSessionId())
//                .userEmail(user.getEmail())
                .userId(user.getId())
                .build();
        //RoomMember 저장하기
        roomUserRepository.save(roomMember);
//        //RoomMaster 권한 부여
//        boolean roomMaster;
//
//        List<RoomMember> roomMemberList = roomUserRepository.findAllBySessionId(savedRoom.getSessionId());
//
//        List<RoomMemberResponseDto> roomUserResponseDtoList = new ArrayList<>();
//
//        for (RoomMember roomUser : roomMemberList){
//
//            if (user != null){
//                roomMaster = Objects.equals(roomUser.getUserNickname(), user.getNickname());
//            } else {
//                roomMaster = false;
//            }
//
//            roomUserResponseDtoList.add(new RoomMemberResponseDto(roomUser, roomMaster));
//
//        }

        Long currentUser = roomUserRepository.countAllBySessionId(newToken.getSessionId());

        room.updateCurrentMember(currentUser);
        //Room 저장하기
        Room savedRoom = roomRepository.save(room);

        return CreateRoomResponseDto.builder()
                .sessionId(savedRoom.getSessionId())
                .masterNickname(savedRoom.getMasterUserNickname())
                .maxUser(savedRoom.getMaxUser())
                .currentMember(savedRoom.getCurrentMember())
                .token(newToken.getToken())
                .build();
    }

    public CreateRoomResponseDto createNewToken(User user, String sessionId) throws OpenViduJavaClientException, OpenViduHttpException{
        //userNickname을 serverData로 받기
        String serverData = user.getNickname();

        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC).data(serverData).build();
        //sessionId를 방 제목으로
        Map<String, Object> params = new HashMap<>();
        params.put("sessionId", sessionId);
        SessionProperties sessionProperties = SessionProperties.fromJson(params).build();
        //session만들기
        Session session = openVidu.createSession(sessionProperties);
        //토큰 받아오기
        String token = session.createConnection(connectionProperties).getToken();

        return CreateRoomResponseDto.builder()
                .sessionId(session.getSessionId())
                .token(token)
                .build();
    }

    //======================================================================================================//

    //방 접속
    public RoomMemberResponseDto enterRoom(String sessionId, User user) throws OpenViduJavaClientException, OpenViduHttpException {

        Room room = roomRepository.findById(sessionId).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );

        Optional<RoomMember> alreadyRoomUser = roomUserRepository.findBySessionIdAndUserNickname(sessionId, user.getNickname());

        if (alreadyRoomUser.isPresent()){
            throw new CheckApiException(ErrorCode.ALREADY_ENTER_USER);
        }

        String enterRoomToken = enterRoomToken(user, room.getSessionId());

        RoomMember roomMember = RoomMember.builder()
                .sessionId(room.getSessionId())
                .userNickname(user.getNickname())
                .userEmail(user.getEmail())
                .enterRoomToken(enterRoomToken)
                .build();

        roomUserRepository.save(roomMember);

        boolean roomMaster = false;

        List<RoomMember> roomMemberList = roomUserRepository.findAllBySessionId(room.getSessionId());

        List<RoomMemberResponseDto> roomMemberResponseDtoList = new ArrayList<>();

        for (RoomMember addRoomMember : roomMemberList){

            if (user != null){
                roomMaster = Objects.equals(addRoomMember.getUserNickname(), user.getNickname());
            } else {
                roomMaster = false;
            }

            roomMemberResponseDtoList.add(new RoomMemberResponseDto(addRoomMember, roomMaster));

        }

        Long currentMember = roomUserRepository.countAllBySessionId(room.getSessionId());

        room.updateCurrentMember(currentMember);

        roomRepository.save(room);

        return RoomMemberResponseDto.builder()
                .roomMemberId(roomMember.getRoomMemberId())
                .sessionId(roomMember.getSessionId())
                .userNickname(roomMember.getUserNickname())
                .userEmail(roomMember.getUserEmail())
                .enterRoomToken(roomMember.getEnterRoomToken())
                .roomMaster(roomMaster)
                .build();
    }

    private String enterRoomToken(User user, String sessionId) throws OpenViduHttpException, OpenViduJavaClientException {

        String serverData = user.getNickname();

        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC).data(serverData).build();

        openVidu.fetch();

        List<Session> activeSessionList = openVidu.getActiveSessions();

        Session session = null; // 세션 초기화

        for (Session getSession : activeSessionList) {
            if (getSession.getSessionId().equals(sessionId)){
                session = getSession;
                break;
            }
        }

        if (session == null){
            throw new CheckApiException(ErrorCode.NOT_EXITS_ROOM);
        }

        return session.createConnection(connectionProperties).getToken();

    }

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
