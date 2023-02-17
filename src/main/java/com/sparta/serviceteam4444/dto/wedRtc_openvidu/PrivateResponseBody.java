package com.sparta.serviceteam4444.dto.wedRtc_openvidu;

import com.sparta.serviceteam4444.exception.OpenViduErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrivateResponseBody {

    private String statusCode;

    private String statusMessage;

    private Object data;


    public PrivateResponseBody(OpenViduErrorCode errorCode, Object data) {
        this.statusCode = errorCode.getErrorCode();
        this.statusMessage = errorCode.getErrorMessage();
        this.data = data;
    }
}
