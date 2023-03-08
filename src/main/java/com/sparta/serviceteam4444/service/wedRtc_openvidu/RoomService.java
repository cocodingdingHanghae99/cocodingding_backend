package com.sparta.serviceteam4444.service.wedRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.*;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import com.sparta.serviceteam4444.exception.CheckApiException;
import com.sparta.serviceteam4444.exception.ErrorCode;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomMemberRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomRepository;
import com.sparta.serviceteam4444.security.user.UserDetailsImpl;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {

    private OpenVidu openVidu;

    private final RoomRepository roomRepository;

    private final RoomMemberRepository roomMemberRepository;

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    @PostConstruct
    public OpenVidu openVidu(){
        return this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }
    @Transactional
    //방만들기
    public RoomCreateResponseDto createRoom(RoomCreateRequestDto roomCreateRequestDto, UserDetailsImpl userDetails)
            throws OpenViduJavaClientException, OpenViduHttpException {
        //방 비밀번호가 비어있으면 예외처리.
        if(roomCreateRequestDto.isStatus() && roomCreateRequestDto.getPassword() == null){
            throw new CheckApiException(ErrorCode.EMPTY_ROOM_PASSWORD);
        }
        //새로운 session 생성
        CreateSessionResponseDto newToken = createNewToken(userDetails.getUser().getUserNickname());
        //roomMember 저장하기(방장권한을 true로)
        RoomMember roomMember = new RoomMember(userDetails.getUser(), true, newToken);
        roomMember = roomMemberRepository.save(roomMember);
        //방을 만든 사람의 userId를 roomMaster로 room 빌드
        Room room = new Room(newToken, roomCreateRequestDto, roomMember.getRoomMemberId());
        //room 저장
        room = roomRepository.save(room);
        //현제 인원 불러오기
        Long currentMember = roomMemberRepository.countAllBySessionId(roomMember.getSessionId());
        //현제 인원을 room에 저장.
        room.updateCRTMember(currentMember);
        //return
        return new RoomCreateResponseDto(room, roomMember);
    }

    //session 생성 및 token 받아오기
    private CreateSessionResponseDto createNewToken(String userNickname)
            throws OpenViduJavaClientException, OpenViduHttpException {
        //userNickname을 serverData로 session과 connection 만들기
        ConnectionProperties properties = new ConnectionProperties.Builder()
                .data(userNickname)
                .role(OpenViduRole.PUBLISHER)
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
    public RoomCreateResponseDto enterRoom(Long roomId, UserDetailsImpl userDetails, EnterRoomDto enterRoomDto)
            throws OpenViduJavaClientException, OpenViduHttpException {
        //roomId를 이용해서 room 찾기.
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );
        //비밀번호 틀리면 예외처리.
        if(room.isStatus() && !room.getPassword().equals(enterRoomDto.getPassword())){
            throw new CheckApiException(ErrorCode.NOT_EQUALS_PASSWORD);
        }
        //새로운 토큰.
        CreateEnterRoomTokenDto newEnterRoomToken = createEnterRoomToken(room.getSessoinId(),
                userDetails.getUser().getUserNickname());
        //중복 입장 처리.
        List<RoomMember> roomMemberList = roomMemberRepository.findAllBySessionId(room.getSessoinId());
        for(RoomMember checkRoomMember: roomMemberList){
            //중복입장이라면 토큰만 새로 발급 (다른방을 갔다가 왔을수도 있기때문에)
            if(Objects.equals(checkRoomMember.getUser().getId(), userDetails.getUser().getId())){
                checkRoomMember.updateToken(newEnterRoomToken.getNewEnterRoomToken());
                return new RoomCreateResponseDto(room, checkRoomMember);
            }
        }
        //중복입장이 아니라면 새로 roomMember 저장하기
        //4명 이상이라면 예외처리.
        if(roomMemberRepository.countAllBySessionId(room.getSessoinId()) == 4){
            throw new CheckApiException(ErrorCode.ALREADY_FULL_ROOM);
        }
        RoomMember roomMember = new RoomMember(userDetails.getUser(), false, newEnterRoomToken, room.getSessoinId());
        roomMemberRepository.save(roomMember);
        //현재 인원 불러오기
        Long currentMember = roomMemberRepository.countAllBySessionId(roomMember.getSessionId());
        //현재 인원을 room에 저장.
        room.updateCRTMember(currentMember);
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
                .role(OpenViduRole.PUBLISHER)
                .type(ConnectionType.WEBRTC)
                .build();
        //connection
        Connection connection = session.createConnection(properties);
        //token 발급
        return new CreateEnterRoomTokenDto(connection);
    }

    //전체 방 목록 보여주기
    public ResponseDto getAllRooms(int page) {
        //방 목록을 6개씩 묶어서 페이지 처리
        List<GetRoomResponseDto> getRoomResponseDtos = new ArrayList<>();
        String message = "방 불러오기 성공";
        int statusCode = 200;
        for(int i = 0; i < page; i++){
            PageRequest pageable = PageRequest.of(i, 6);
            PageRequest pageable1 = PageRequest.of(i + 1, 6);
            Page<Room> roomList = roomRepository.findByOrderByModifiedAtDesc(pageable);
            Page<Room> roomList1 = roomRepository.findByOrderByModifiedAtDesc(pageable1);
            if(roomList1.isEmpty()){
                message = "불러올 방이 없습니다";
                statusCode = 204;
                break;
            }
            for(Room room : roomList){
                RoomMember roomMaster = roomMemberRepository.findById(room.getRoomMasterId()).orElseThrow(
                        () -> new CheckApiException(ErrorCode.NOT_EXITS_USER)
                );
                String masterUserNickname = roomMaster.getUser().getUserNickname();
                GetRoomResponseDto getRoomResponseDto = new GetRoomResponseDto(room, masterUserNickname);
                getRoomResponseDtos.add(getRoomResponseDto);
            }
        }
        return new ResponseDto(getRoomResponseDtos, statusCode, message);
    }

    //방 나가기
    @Transactional
    public String exitRoom(Long roomId, UserDetailsImpl userDetails) throws OpenViduJavaClientException, OpenViduHttpException {
        //roomId에 맞는 방 찾기
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );
        //user가 방장인지 확인
        if(roomMemberRepository.findByUserAndSessionId(userDetails.getUser(),
                room.getSessoinId()).isRoomMaster()){
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
            RoomMember roomMember = roomMemberRepository.findByUserAndSessionId(userDetails.getUser(),
                    room.getSessoinId());
            openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
            openVidu.fetch();
            Session session = openVidu.getActiveSession(room.getSessoinId());
            session.forceDisconnect(roomMember.getConnectionId());
            //방장이 아니라면 방 멤버만 삭제
            roomMemberRepository.deleteBySessionIdAndUser(room.getSessoinId(),
                    userDetails.getUser());
            //현제 인원을 room에 저장.
            Long currentMember = roomMemberRepository.countAllBySessionId(room.getSessoinId());
            room.updateCRTMember(currentMember);
        }
        return "방을 나갔습니다.";
    }

    //방에 있는 모든 사람 닉네임 가져오기.
    public AllRoomMemberDto getAllRoomMember(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );
        List<RoomMember> roomMemberList = roomMemberRepository.findAllBySessionId(room.getSessoinId());
        List<String> roomMemberNicknameList = new ArrayList<>();
        for(RoomMember roomMember: roomMemberList){
            roomMemberNicknameList.add(roomMember.getUser().getUserNickname());
        }
        return new AllRoomMemberDto(roomMemberNicknameList);
    }

    public List<String> connectionTest(Long roomId) throws OpenViduJavaClientException, OpenViduHttpException {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_ROOM)
        );
        openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
        openVidu.fetch();
        Session session = openVidu.getActiveSession(room.getSessoinId());
        List<Connection> connections = session.getActiveConnections();
        List<String> testConnection = new ArrayList<>();
        for(Connection connection1: connections){
            testConnection.add(connection1.getServerData());
        }
        return testConnection;
    }
}
