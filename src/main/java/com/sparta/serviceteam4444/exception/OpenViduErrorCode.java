package com.sparta.serviceteam4444.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OpenViduErrorCode {

    private HttpStatus httpStatus;

    private String errorCode;

    private String errorMessage;

    public OpenViduErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage){
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
