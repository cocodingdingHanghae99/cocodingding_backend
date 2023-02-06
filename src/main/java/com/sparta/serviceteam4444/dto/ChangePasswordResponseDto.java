package com.sparta.serviceteam4444.dto;

import com.sparta.serviceteam4444.entity.User;

public class ChangePasswordResponseDto {

    private String password;

    public ChangePasswordResponseDto(User user){
        this.password = user.getPassword();
    }
}
