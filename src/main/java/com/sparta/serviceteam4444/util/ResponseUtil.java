package com.sparta.serviceteam4444.util;

import com.sparta.serviceteam4444.dto.wedRtc_openvidu.PrivateResponseBody;
import com.sparta.serviceteam4444.exception.CheckApiException;
import com.sparta.serviceteam4444.exception.OpenViduErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ResponseUtil<T> {
    public ResponseEntity<PrivateResponseBody> forSuccess(T data) {
        return new ResponseEntity<>(
                new PrivateResponseBody(
                        new OpenViduErrorCode(HttpStatus.OK, "200", "정상"), data
                ),
                HttpStatus.OK
        );
    }
}
