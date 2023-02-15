package com.sparta.serviceteam4444.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    SUCCESS_CREATE_SESSION(HttpStatus.OK, "200", "방 생성 성공"),

    PLEASE_LOGIN(HttpStatus.BAD_REQUEST, "400", "로그인 해주세요."),

    NOT_EXITS_ROOM(HttpStatus.BAD_REQUEST, "400", "방이 없습니다."),

    BEN_ROOM(HttpStatus.BAD_REQUEST, "400", "강퇴당한 방입니다.");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
