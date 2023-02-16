package com.sparta.serviceteam4444.entity.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmailMessage {

    private String to;
    private String subject; //제목
    private String message; //내용
}