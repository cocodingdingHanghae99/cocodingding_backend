package com.sparta.serviceteam4444.dto.user;

import com.sparta.serviceteam4444.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String userNickname;

    public UserRequestDto(User user) {
        this.userNickname = user.getUserNickname();
    }

}
