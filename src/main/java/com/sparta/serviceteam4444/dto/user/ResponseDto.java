package com.sparta.serviceteam4444.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

    private T result;

    private boolean success;

    public static <T> ResponseDto<T> success(T result){
        return new ResponseDto<>(result, true);
    }

}
