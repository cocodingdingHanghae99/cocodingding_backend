package com.sparta.serviceteam4444.entity;

import com.sparta.serviceteam4444.dto.ChatRoomRequestDto;
import com.sparta.serviceteam4444.timestamp.Timestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@RequiredArgsConstructor
public class ChatRoom extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String nickname;

    public ChatRoom(ChatRoomRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.nickname = user.getNickname();
    }

}
