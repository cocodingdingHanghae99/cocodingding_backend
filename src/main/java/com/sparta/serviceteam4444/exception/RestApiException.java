package com.sparta.serviceteam4444.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException {
    private String msg;
    private int httpStatus;

    public RestApiException(ErrorCode errorCode) {
        this.httpStatus = errorCode.getErrorCode();
        this.msg = errorCode.getErrorMessage();
    }
}