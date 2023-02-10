package com.sparta.serviceteam4444.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    NOT_VALIDATE_TOKEN(HttpStatus.BAD_REQUEST, "400", "토큰이 유효하지 않습니다."),
    EXISTS_USER(HttpStatus.BAD_REQUEST, "400", "중복된 사용자가 존재합니다."),
    NOT_EQUALS_PASSWORD(HttpStatus.BAD_REQUEST, "400", "비밀번호가 일치하지 않습니다."),
    NOT_EXISTS_USER(HttpStatus.BAD_REQUEST,"400", "등록된 사용자가 없습니다."),
    NOT_EQUALS_USER(HttpStatus.BAD_REQUEST, "400", "사용자가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
