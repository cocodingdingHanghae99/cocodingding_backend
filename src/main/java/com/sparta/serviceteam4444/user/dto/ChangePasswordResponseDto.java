package com.sparta.serviceteam4444.user.dto;

import com.sparta.serviceteam4444.user.entity.User;

public class ChangePasswordResponseDto {

    private String password;

    public ChangePasswordResponseDto(User user){
        this.password = user.getPassword();
    }
}
