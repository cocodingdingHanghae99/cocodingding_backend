package com.sparta.serviceteam4444.dto;

import com.sparta.serviceteam4444.entity.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomResponseDto {
    private Long roomId;
    private String title;
    private String category;
    private String nickname;
    private String createdAt;
    private String modifiedAt;
    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.roomId = chatRoom.getRoomId();
        this.title = chatRoom.getTitle();
        this.category = chatRoom.getCategory();
        this.nickname = chatRoom.getNickname();
        this.createdAt = chatRoom.getCreatedAt();
        this.modifiedAt = chatRoom.getModifiedAt();

    }

}