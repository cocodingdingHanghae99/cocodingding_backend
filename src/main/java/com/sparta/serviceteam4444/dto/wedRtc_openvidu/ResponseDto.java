package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseDto {
    private final List<GetRoomResponseDto> getRoomResponseDtos;
    private final int statusCode;
    private final String message;

    public ResponseDto(List<GetRoomResponseDto> data, int statusCode, String message) {
        this.getRoomResponseDtos = data;
        this.statusCode = statusCode;
        this.message = message;
    }
}
