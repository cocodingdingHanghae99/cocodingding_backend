package com.sparta.serviceteam4444.service.wedRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.*;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import com.sparta.serviceteam4444.exception.CheckApiException;
import com.sparta.serviceteam4444.exception.ErrorCode;
import com.sparta.serviceteam4444.repository.socket.ChatRoomRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomMemberRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomRepository;
import com.sparta.serviceteam4444.security.user.UserDetailsImpl;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private OpenVidu openVidu;

    private final RoomRepository roomRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final RoomMemberRepository roomMemberRepository;

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    @PostConstruct
    public OpenVidu openVidu(){
        return this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }
    //방만들기
    public RoomCreateResponseDto createRoom(RoomCreateRequestDto roomCreateRequestDto, UserDetailsImpl userDetails) throws OpenViduJavaClientException, OpenViduHttpException {
        //새로운 session 생성
        CreateSessionResponseDto newToken = createNewToken(userDetails.getUser().getUserNickname());
        //방을 만든 사람의 닉네임을 roomMaster로
        String roomMasterNickname = userDetails.getUser().getUserNickname();
        boolean roomMaster = true;
        //room build
        Room room = new Room(newToken, roomCreateRequestDto, roomMasterNickname);
        //roomMember 저장하기.
        RoomMember roomMember = new RoomMember(userDetails.getUser().getUserNickname(), roomMaster, room.getSessoinId());
        roomMemberRepository.save(roomMember);
        //현제 인원 불러오기
        Long currentMember = roomMemberRepository.countAllBySessionId(roomMember.getSessionId());
        //현제 인원을 room에 저장.
        room.updateCRTMember(currentMember);
        //room저장하기.
        roomRepository.save(room);
        //채팅방도 같이 만들기
        chatRoomRepository.createChatRoom(room.getOpenviduRoomId(), room.getRoomTitle(),room.getCategory());
        //return
        return new RoomCreateResponseDto(room, newToken.getToken(), roomMember);
    }

    //session 생성 및 token 받아오기
    private CreateSessionResponseDto createNewToken(String userNickname) throws OpenViduJavaClientException, OpenViduHttpException {
        //userNickname을 serverData로 session과 connection 만들기
        ConnectionProperties properties = new ConnectionProperties.Builder()
                .data(userNickname)
                .type(ConnectionType.WEBRTC)
                .build();
        //새로운 openvidu 체팅방 생성
        Session session = openVidu.createSession();
        //token 받아오기
        String token = session.createConnection(properties).getToken();
        //sessionId, token 리턴
        return new CreateSessionResponseDto(session.getSessionId(), token);
    }

    //방 입장
    public RoomCreateResponseDto enterRoom(Long roomId) throws OpenViduJavaClientException, OpenViduHttpException {

        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );
        String newEnterRoomToken = createEnterRoomToken(room.getSessoinId());
        return null;
//        return new RoomCreateResponseDto(room, newEnterRoomToken);
    }

    //connection 생성 및 token 발급
    private String createEnterRoomToken(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException{
        openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
        openVidu.fetch();
        //sessionId 를 이용하여 session 찾기
        Session session = openVidu.getActiveSession(sessionId);
        //session이 활성화가 안되어 있을때
        if(session == null){
            throw new CheckApiException(ErrorCode.NOT_EXITS_ROOM);
        }
        //connection 생성
        ConnectionProperties properties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).build();
        //token 발급
        return session.createConnection(properties).getToken();
    }
    //전체 방 목록 보여주기
    public List<GetRoomResponseDto> getAllRooms() {
        List<Room> roomList = roomRepository.findAll();
        List<GetRoomResponseDto> getRoomResponseDtos = new ArrayList<>();
        for(Room room : roomList){
            GetRoomResponseDto getRoomResponseDto = new GetRoomResponseDto(room);
            getRoomResponseDtos.add(getRoomResponseDto);
        }
        return getRoomResponseDtos;
    }
//    //일반 맴버 방 나가기
//    public ExitRoomDto memberExitRoom(Long roomId) {
//        Room room = roomRepository.findById(roomId).orElseThrow(
//                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
//        );
//        openVidu.getActiveSession(room.getSessoinId());
//    }
}
