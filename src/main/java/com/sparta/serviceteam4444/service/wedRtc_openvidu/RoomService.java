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
    public CreateRoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto
                                            /*User user*/) throws OpenViduJavaClientException, OpenViduHttpException{
        //session생성 및 token받아오기
        CreateRoomResponseDto newToken = createNewToken(/*user*/);

        //newToken바탕으로 Room build
        Room room = Room.builder()
                .roomTitle(createRoomRequestDto.getRoomTitle())
                .sessionId(newToken.getSessionId())
                //방 만든사람이 masterUser
//                .masterUserNickname(user.getUserNickname())
                .enterRoomToken(newToken.getToken())
                .build();

        RoomMember roomMember = RoomMember.builder()
                .sessionId(newToken.getSessionId())
//                .userId(user.getId())
                .enterRoomToken(newToken.getToken())
//                .userNickname(user.getUserNickname())
                .build();
        //roomMember 저장하기
        roomUserRepository.save(roomMember);
        //방에 있는 인원 체크
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
    //session 만들기 및 토큰 발급
    public CreateRoomResponseDto createNewToken(/*User user*/) throws OpenViduJavaClientException, OpenViduHttpException{
//        //userNickname을 serverData로 받기
//        String serverData = user.getUserNickname();

        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)/*.data(serverData)*/.build();
        //session만들기
        Session session = openVidu.createSession();
        //토큰 받아오기
        String token = session.createConnection(connectionProperties).getToken();

        return CreateRoomResponseDto.builder()
                .sessionId(session.getSessionId())
                .token(token)
                .build();
    }

    //======================================================================================================//

    //방 접속
    public ResponseEntity<String> enterRoom(String sessionId/*, User user*/) throws OpenViduJavaClientException, OpenViduHttpException {
        //session 가져오기
        Session session = openVidu.getActiveSession(sessionId);
        //없으면 예외처리
        if (session == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //db에서 room 정보 찾기
        Room room = roomRepository.findById(sessionId).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );
        /*//룸에 있는 멤버인지 확인
        Optional<RoomMember> alreadyExistMember = roomUserRepository.findBySessionIdAndUserNickname(sessionId, user.getUserNickname());
        if(alreadyExistMember.isPresent()){
            throw new CheckApiException(ErrorCode.ALREADY_ENTER_USER);
        }*/
        //토큰 발급
        String enterRoomToken = enterRoomCreateSession(/*user,*/ sessionId);
        //roomMember 빌드
        RoomMember roomMember = RoomMember.builder()
                .sessionId(room.getSessionId())
//                .userNickname(user.getUserNickname())
//                .userId(user.getId())
                .enterRoomToken(enterRoomToken)
                .build();
        //roomMember 저장
        roomUserRepository.save(roomMember);
/*        //roomMaster인지 판단.
        boolean roomMaster;

        List<RoomMember> roomMemberList = roomUserRepository.findAllBySessionId(room.getSessionId());

        List<RoomMemberResponseDto> roomMemberResponseDtoList = new ArrayList<>();

        for (RoomMember addRoomMember : roomMemberList){

            if (user != null){
                roomMaster = Objects.equals(addRoomMember.getUserNickname(), user.getUserNickname());
            } else {
                roomMaster = false;
            }

            roomMemberResponseDtoList.add(new RoomMemberResponseDto(addRoomMember, roomMaster));

        }*/

        Long currentMember = roomUserRepository.countAllBySessionId(room.getSessionId());

        room.updateCurrentMember(currentMember);

        roomRepository.save(room);

        ConnectionProperties properties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
//                .data(user.getUserNickname())
                .build();

        openVidu.fetch();

        //오픈비두에 활성화된 세션을 모두 가져와 리스트에 담음
        List<Session> activeSessionList = openVidu.getActiveSessions();

        Connection connection = session.createConnection(properties);

        return new ResponseEntity<>(connection.getToken(), HttpStatus.OK);
    }

    //방 입장 시 (입장하는 유저에 맞는) 토큰 생성
    private String enterRoomCreateSession(/*User user, */String sessionId)throws
            OpenViduJavaClientException, OpenViduHttpException{
//        //user닉네임을 servserData로
//        String serverData = user.getUserNickname();
        //serverData을 사용하여 connectionProperties 객체를 빌드
        ConnectionProperties properties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
//                .data(serverData)
                .build();

        openVidu.fetch();
        //openvidu에 sessionId와 맞는 활성화된 세션을 받아오기
        Session activeSession = openVidu.getActiveSession(sessionId);
        if(activeSession == null){
            throw new CheckApiException(ErrorCode.NOT_EXITS_ROOM);
        }
        return activeSession.createConnection(properties).getToken();
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

    public RoomResponseDto getRoom(String roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                ()-> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );
        return RoomResponseDto.builder()
                .roomTitle(room.getRoomTitle())
                .sessionId(room.getSessionId())
                .enterRoomToken(room.getEnterRoomToken())
                .build();
    }
}
