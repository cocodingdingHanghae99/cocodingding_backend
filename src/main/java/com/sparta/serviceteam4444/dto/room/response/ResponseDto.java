package com.sparta.serviceteam4444.dto.room.response;

import com.sparta.serviceteam4444.exception.RestApiExceptionHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String data;

    private int statuscode;

    public ResponseDto(String data, int statuscode) {
        this.data = data;
        this.statuscode = statuscode;
    }
}
