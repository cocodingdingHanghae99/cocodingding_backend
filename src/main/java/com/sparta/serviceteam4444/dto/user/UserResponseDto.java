package com.sparta.serviceteam4444.dto.user;

import com.sparta.serviceteam4444.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String data;

    private int statuscode;

    private String userEmail;

    private String userNickname;

    public UserResponseDto(String data, int statuscode) {
        this.data = data;
        this.statuscode = statuscode;
    }
}
