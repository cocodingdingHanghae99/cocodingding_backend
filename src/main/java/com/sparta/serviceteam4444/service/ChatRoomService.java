package com.sparta.serviceteam4444.service;

import com.sparta.serviceteam4444.dto.ChatRoomRequestDto;
import com.sparta.serviceteam4444.dto.ChatRoomResponseDto;
import com.sparta.serviceteam4444.entity.ChatRoom;
import com.sparta.serviceteam4444.entity.User;
import com.sparta.serviceteam4444.entity.UserRoleEnum;
import com.sparta.serviceteam4444.jwt.JwtUtil;
import com.sparta.serviceteam4444.repository.ChatRoomRepository;
import com.sparta.serviceteam4444.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatRoomResponseDto createRoom(ChatRoomRequestDto chatRoomRequestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByNickname(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            ChatRoom chatRoom =new ChatRoom(chatRoomRequestDto, user);
            chatRoomRepository.save(chatRoom);
            return new ChatRoomResponseDto(chatRoom);
    }


    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> getRooms() {

        List<ChatRoomResponseDto> list = new ArrayList<>();

        List<ChatRoom> chatRoomList;
        chatRoomList = chatRoomRepository.findAllByOrderByCreatedAtDesc();

        for (ChatRoom chatRoom : chatRoomList) {
            list.add(new ChatRoomResponseDto(chatRoom));
        }

        return list;
    }


}