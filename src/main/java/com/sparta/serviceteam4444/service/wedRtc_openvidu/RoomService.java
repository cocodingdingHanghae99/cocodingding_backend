package com.sparta.serviceteam4444.service.wedRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.CreateSessionResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.GetRoomResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateRequestDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateResponseDto;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import com.sparta.serviceteam4444.exception.CheckApiException;
import com.sparta.serviceteam4444.exception.ErrorCode;
import com.sparta.serviceteam4444.repository.socket.ChatRoomRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomRepository;
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

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    @PostConstruct
    public OpenVidu openVidu(){
        return this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }
    //방만들기
    public RoomCreateResponseDto createRoom(RoomCreateRequestDto roomCreateRequestDto) throws OpenViduJavaClientException, OpenViduHttpException {
        //새로운 session 생성
        CreateSessionResponseDto newToken = createNewToken();
        //room build
        Room room = new Room(newToken, roomCreateRequestDto);
        //room저장하기.
        roomRepository.save(room);
        //채팅방도 같이 만들기
        chatRoomRepository.createChatRoom(room.getOpenviduRoomId(), room.getRoomTitle(),room.getCategory());
        //return
        return new RoomCreateResponseDto(room, newToken.getToken());
    }

    //session 생성 및 token 받아오기
    private CreateSessionResponseDto createNewToken() throws OpenViduJavaClientException, OpenViduHttpException {
        ConnectionProperties properties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).build();
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
        return new RoomCreateResponseDto(room, newEnterRoomToken);
    }

    //connection 생성 및 token 발급
    private String createEnterRoomToken(String sessionId) throws OpenViduJavaClientException, OpenViduHttpException{
        if(openVidu.getActiveSessions().isEmpty()){
            openVidu.fetch();
        }else {
            openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
            openVidu.fetch();
        }
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

    public List<GetRoomResponseDto> getAllRooms() {
        List<Room> roomList = roomRepository.findAll();
        List<GetRoomResponseDto> getRoomResponseDtos = new ArrayList<>();
        for(Room room : roomList){
            GetRoomResponseDto getRoomResponseDto = new GetRoomResponseDto(room);
            getRoomResponseDtos.add(getRoomResponseDto);
        }
        return getRoomResponseDtos;
    }
    
}
