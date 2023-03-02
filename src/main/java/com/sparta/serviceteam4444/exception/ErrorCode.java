package com.sparta.serviceteam4444.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    SUCCESS_CREATE_SESSION(HttpStatus.OK, 200, "방 생성 성공."),

    NOT_MATCH_TOKEN(HttpStatus.BAD_REQUEST, 400, "토큰값이 일치하지 않습니다."),

    PLEASE_LOGIN(HttpStatus.BAD_REQUEST, 400, "로그인 해주세요."),

    EXITS_EMAIL(HttpStatus.BAD_REQUEST, 400, "이미 가입되어있는 이메일 입니다."),

    NOT_EXITS_USER(HttpStatus.BAD_REQUEST, 400, "존재하지 않는 사용자입니다."),

    NOT_EXITS_ROOM(HttpStatus.BAD_REQUEST, 400, "방이 없습니다."),

    NOT_EQUALS_PASSWORD(HttpStatus.BAD_REQUEST, 400, "비밀번호가 일치하지 않습니다."),

    ALREADY_ENTER_USER(HttpStatus.BAD_REQUEST, 400, "이미 입장한 유저입니다."),

    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, 400, "방 비밀번호를 입력하세요"),
    ALREADY_FULL_ROOM(HttpStatus.BAD_REQUEST, 400, "방이 가득 찼습니다."),

    BEN_ROOM(HttpStatus.BAD_REQUEST, 400, "강퇴당한 방입니다.");


    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, int errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
