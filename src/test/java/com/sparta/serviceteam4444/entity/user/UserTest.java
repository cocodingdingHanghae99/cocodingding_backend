package com.sparta.serviceteam4444.entity.user;

import com.sparta.serviceteam4444.dto.user.UserLoginDto;
import com.sparta.serviceteam4444.dto.user.UserSignupDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("정상 케이스")
    void createUser() throws Exception{

        //given
        String userEmail = "test";
        String userNickname = "test";
        String userPassword = "test";

        //when
        User user = new User(userEmail, userNickname, userPassword);

        //then
        assertEquals(userEmail, user.getUserEmail());
        assertEquals(userNickname, user.getUserNickname());
        assertEquals(userPassword, user.getUserPassword());

    }

}