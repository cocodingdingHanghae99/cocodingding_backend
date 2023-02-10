package com.sparta.serviceteam4444.dto.room.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateRoomRequestDto {
    private String RoomTitle;

    private Long maxMember;

    private boolean status;

    private String password;
}
