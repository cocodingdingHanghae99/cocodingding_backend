package com.sparta.serviceteam4444.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestApiException {
    private String msg;
    private int httpStatus;
}