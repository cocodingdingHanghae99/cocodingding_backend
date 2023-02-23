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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public OpenVidu openVidu(){
        return this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }
    //방만들기
    public RoomCreateResponseDto createRoom(RoomCreateRequestDto roomCreateRequestDto, UserDetailsImpl userDetails)
            throws OpenViduJavaClientException, OpenViduHttpException {
        //새로운 session 생성
        CreateSessionResponseDto newToken = createNewToken(userDetails.getUser().getUserNickname());
        //방을 만든 사람의 닉네임을 roomMaster로
        String roomMasterNickname = userDetails.getUser().getUserNickname();
        boolean roomMaster = true;
        //room build
        Room room = new Room(newToken, roomCreateRequestDto, roomMasterNickname);
        //roomMember 저장하기.
        RoomMember roomMember = new RoomMember(userDetails.getUser().getUserNickname(),
                roomMaster, room.getSessoinId(), newToken.getToken(), newToken.getConnectionId());
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
        return new RoomCreateResponseDto(room, roomMember);
    }

    //session 생성 및 token 받아오기
    private CreateSessionResponseDto createNewToken(String userNickname)
            throws OpenViduJavaClientException, OpenViduHttpException {
        //userNickname을 serverData로 session과 connection 만들기
        ConnectionProperties properties = new ConnectionProperties.Builder()
                .data(userNickname)
                .type(ConnectionType.WEBRTC)
                .build();
        //새로운 openvidu 체팅방 생성
        Session session = openVidu.createSession();
        //connection
        Connection connection = session.createConnection(properties);
        //sessionId, token 리턴
        return new CreateSessionResponseDto(session.getSessionId(), connection);
    }

    @Transactional
    //방 입장
    public RoomCreateResponseDto enterRoom(Long roomId, UserDetailsImpl userDetails)
            throws OpenViduJavaClientException, OpenViduHttpException {
        //userDetails 가 null일때.
        if(userDetails == null){
            throw new CheckApiException(ErrorCode.NOT_EXITS_USER);
        }
        //roomId를 이용해서 room 찾기.
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );
        //방장인지 아닌지 판단 및 중복입장 처리.
        RoomMember roomMember = new RoomMember();
        CreateEnterRoomTokenDto newEnterRoomToken = createEnterRoomToken(room.getSessoinId(), userDetails.getUser().getUserNickname());
        //room에 맞는 sessionId를 가진 roomMember 전부 찾기.
        List<RoomMember> roomMemberList = roomMemberRepository.findAllBySessionId(room.getSessoinId());
        for(RoomMember checkRoomMember: roomMemberList){
            //중복입장 이라면 토큰만 바꿔서 내보내기.
            if(checkRoomMember.getUserNickname().equals(userDetails.getUser().getUserNickname())) {
                checkRoomMember.updateToken(newEnterRoomToken.getNewEnterRoomToken());
                return new RoomCreateResponseDto(room, checkRoomMember);
            }
        }
        //roomMaster 와 nickname이 일치하면 roomMaster = true;
        for(RoomMember checkRoomMaster: roomMemberList){
            if(!checkRoomMaster.getUserNickname().equals(userDetails.getUser().getUserNickname())){
                //일치하지 않는다면 새로운 roomMember를 저장하자.
                roomMember = new RoomMember(userDetails.getUser().getUserNickname(),
                        false, room.getSessoinId(), newEnterRoomToken);
                roomMemberRepository.save(roomMember);
                Long currentMember = roomMemberRepository.countAllBySessionId(roomMember.getSessionId());
                //현제 인원을 room에 저장.
                room.updateCRTMember(currentMember);
            }else {
                //일치한다면 이미 만들어져있는 roomMember를 불러오자.
                roomMember = roomMemberRepository.findByUserNicknameAndSessionId(userDetails.getUser().getUserNickname(),
                        room.getSessoinId());
                roomMember.updateToken(newEnterRoomToken.getNewEnterRoomToken());
                break;
            }
        }
        return new RoomCreateResponseDto(room, roomMember);
    }

    //connection 생성 및 token 발급
    private CreateEnterRoomTokenDto createEnterRoomToken(String sessionId, String userNickname)
            throws OpenViduJavaClientException, OpenViduHttpException{
        openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
        openVidu.fetch();
        //sessionId 를 이용하여 session 찾기
        Session session = openVidu.getActiveSession(sessionId);
        //session이 활성화가 안되어 있을때
        if(session == null){
            throw new CheckApiException(ErrorCode.NOT_EXITS_ROOM);
        }
        //userNickname을 serverData로 connection 생성
        ConnectionProperties properties = new ConnectionProperties.Builder()
                .data(userNickname)
                .type(ConnectionType.WEBRTC)
                .build();
        //connection
        Connection connection = session.createConnection(properties);
        //token 발급
        return new CreateEnterRoomTokenDto(connection);
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
    //방 나가기
    @Transactional
    public String exitRoom(Long roomId, UserDetailsImpl userDetails) throws OpenViduJavaClientException, OpenViduHttpException {
        //roomId에 맞는 방 찾기
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );
        //user가 방장인지 확인
        if(roomMemberRepository.findByUserNicknameAndSessionId(userDetails.getUser().getUserNickname()
                ,room.getSessoinId()).isRoomMaster()){
            //방장이라면 방 멤버와 방을 삭제
            roomMemberRepository.deleteBySessionId(room.getSessoinId());
            roomRepository.delete(room);
            //openVidu session 삭제
            openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
            openVidu.fetch();
            Session session = openVidu.getActiveSession(room.getSessoinId());
            session.close();
            return "체팅방이 삭제되었습니다";
        }else {
            //openVidu 연결 끊기
            RoomMember roomMember = roomMemberRepository.findByUserNicknameAndSessionId(userDetails.getUser().getUserNickname(),
                    room.getSessoinId());
            openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
            openVidu.fetch();
            Session session = openVidu.getActiveSession(room.getSessoinId());
            session.forceDisconnect(roomMember.getConnectionId());
            //방장이 아니라면 방 멤버만 삭제
            roomMemberRepository.deleteBySessionIdAndUserNickname(room.getSessoinId(),
                    userDetails.getUser().getUserNickname());
            //현제 인원을 room에 저장.
            Long currentMember = roomMemberRepository.countAllBySessionId(room.getSessoinId());
            room.updateCRTMember(currentMember);
        }
        return "방을 나갔습니다.";
    }
}
