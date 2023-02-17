package com.sparta.serviceteam4444.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupDto {

    private String userEmail;

    private String userNickname;

    private String userPassword;

}
