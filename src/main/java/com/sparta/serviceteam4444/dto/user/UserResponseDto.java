package com.sparta.serviceteam4444.dto.user;

import com.sparta.serviceteam4444.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private String data;

    private int statuscode;

    private String userEmail;

    private String userNickname;

    private String token;

    public UserResponseDto(String data, int statuscode) {
        this.data = data;
        this.statuscode = statuscode;
    }

}
