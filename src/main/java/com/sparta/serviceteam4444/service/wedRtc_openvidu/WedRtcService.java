package com.sparta.serviceteam4444.service.wedRtc_openvidu;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateResponseDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomCreateRequestDto;
import com.sparta.serviceteam4444.dto.wedRtc_openvidu.RoomMemberResponseDto;
import com.sparta.serviceteam4444.entity.user.User;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.Room;
import com.sparta.serviceteam4444.entity.webRtc_openvidu.RoomMember;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomMemberRepository;
import com.sparta.serviceteam4444.repository.wedRtc_openvidu.RoomRepository;
import com.sparta.serviceteam4444.security.user.UserDetailsImpl;
import io.openvidu.java.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WedRtcService {
    //SDK의 진입점인 OpenVidu 객체
    private OpenVidu openVidu;
    private RoomRepository roomRepository;
    private RoomMemberRepository roomMemberRepository;
    //OpenVidu 서버가 수신하는 URL
    @Value("${openvidu.url}")
    private String OPENVIDU_URL;
    //OpenVidu 서버와 공유되는 secret 키
    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;
    //Openvicu 객체에 url과 secret 키를 넣어주기
    @PostConstruct
    public OpenVidu openVidu(){
        return openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }
    //방 만들기
    public RoomCreateResponseDto createRoom(RoomCreateRequestDto roomCreateRequestDto, UserDetailsImpl userDetails)
            throws OpenViduJavaClientException, OpenViduHttpException {
        //userNickname 가져오기
        User user = userDetails.getUser();
        //room 생성하기 - createNewToken 메소드에 openvidu 세션 생성 메소드 포함.
        RoomCreateResponseDto newtoken = createNewToken(user);
        //체팅방 빌드
        Room room = Room.builder()
                .sessionId(newtoken.getSessionId())
                .roomName(roomCreateRequestDto.getRoomName())
                .masterUserNickname(user.getNickname())
                .category(roomCreateRequestDto.getCategory())
                .build();
        //체팅방 인원 빌드
        RoomMember roomMembers = RoomMember.builder()
                .sessionId(newtoken.getSessionId())
                .userId(user.getId())
                .nickname(user.getNickname())
                .enterRoomToken(newtoken.getSessionId())
                .build();
        //체팅방 인원 저장하기
        roomMemberRepository.save(roomMembers);
        //masterUser 인지 아닌지를 판단 후 user 추가하기
        boolean roomMaster;
        List<RoomMember> roomMemberList = roomMemberRepository.findAllBySessionId(newtoken.getSessionId());
        List<RoomMemberResponseDto> roomMemberResponseDtoList = new ArrayList<>();
        //체팅방 인원 추가
        for(RoomMember roomMember: roomMemberList){
            if(user != null){
                //roomMaster인지 아닌지 판별
                roomMaster = Objects.equals(roomMember.getNickname(), user.getNickname());
            }else {
                roomMaster = false;
            }
            roomMemberResponseDtoList.add(new RoomMemberResponseDto(roomMember, roomMaster));
        }
        Long currentMember =roomMemberRepository.countAllBySessionId(newtoken.getSessionId());
        room.updateCurrentMember(currentMember);
        //체팅방 저장
        roomRepository.save(room);
        return RoomCreateResponseDto.builder()
                .sessionId(newtoken.getSessionId())
                .roomName(newtoken.getRoomName())
                .masterNickname(user.getNickname())
                .category(roomCreateRequestDto.getCategory())
                .currentMember(currentMember)
                .roomMemberResponseDtoList(roomMemberResponseDtoList)
                .token(newtoken.getToken())
                .build();
    }
    //체팅방 생성시 코튼 발급.
    private RoomCreateResponseDto createNewToken(User user)throws OpenViduJavaClientException, OpenViduHttpException {
        //사용자 연결 시 닉네임 전달
        String userNickName = user.getNickname();
        //받은 닉네임으로 ConnectionProperties 빌드
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder()
                .type(ConnectionType.WEBRTC)
                .data(userNickName).build();
        //새로운 Openvidu 세션(체팅방)생성
        Session session =openVidu.createSession();
        //토큰 받기
        String token =session.createConnection(connectionProperties).getToken();
        //빌드 리턴.
        return RoomCreateResponseDto.builder()
                .sessionId(session.getSessionId())
                .token(token)
                .build();
    }
}
