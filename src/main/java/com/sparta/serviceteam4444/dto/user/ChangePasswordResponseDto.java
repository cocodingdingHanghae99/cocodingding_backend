package com.sparta.serviceteam4444.dto.user;

import com.sparta.serviceteam4444.entity.user.User;

public class ChangePasswordResponseDto {

    private String password;

    public ChangePasswordResponseDto(User user){
        this.password = user.getPassword();
    }
}
