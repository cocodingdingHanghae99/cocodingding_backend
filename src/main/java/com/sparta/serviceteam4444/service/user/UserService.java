package com.sparta.serviceteam4444.service.user;

import com.sparta.serviceteam4444.dto.user.*;
import com.sparta.serviceteam4444.entity.user.User;
import com.sparta.serviceteam4444.exception.CheckApiException;
import com.sparta.serviceteam4444.exception.ErrorCode;
import com.sparta.serviceteam4444.repository.user.UserRepository;

import com.sparta.serviceteam4444.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
//============================================================================================================//
    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    //============================================================================================================//

    public void signup(UserSignupDto userSignupDto) {

        String userEmail = userSignupDto.getUserEmail();

        String userNickname = userSignupDto.getUserNickname();

        String userPassword = passwordEncoder.encode(userSignupDto.getUserPassword());

        //이메일 중복 검사
        Optional<User> userNameDuplicate = userRepository.findByUserEmail(userSignupDto.getUserEmail());

        if (userNameDuplicate.isPresent()) {
            throw new CheckApiException(ErrorCode.EXITS_EMAIL);
        }

        User user = new User(userEmail, userNickname, userPassword);

        userRepository.save(user);

    }

    //============================================================================================================//

    public UserResponseDto login(UserLoginDto userLoginDto, HttpServletResponse response){

        String userEmail = userLoginDto.getUserEmail();

        String userPassword = userLoginDto.getUserPassword();

        String data = "로그인 성공";

        int statucode = 200;

        User user = userRepository.findByUserEmail(userEmail).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_USER)
        );

        if (!passwordEncoder.matches(userPassword, user.getUserPassword())){
            throw new CheckApiException(ErrorCode.NOT_EQUALS_PASSWORD);
        }

        String refreshToken = jwtUtil.createRefreshToken();

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserEmail()));
        response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);

        user.updateRefreshToken(refreshToken);

        return new UserResponseDto(data, statucode, user.getUserEmail(), user.getUserNickname());

    }

    //============================================================================================================//

    public UserRequestDto updateNickname(String userNickname, UserRequestDto userRequestDto) {

        User user = userRepository.findByUserNickname(userNickname).orElseThrow(
                () -> new CheckApiException(ErrorCode.NOT_EXITS_USER)
        );

        if (user.getUserNickname().equals(userNickname)){
            user.updateNickname(userRequestDto);
        }

        return new UserRequestDto(user);

    }

    //============================================================================================================//

    public void refreshToken(String refresh, String userEmail, HttpServletResponse response){

        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(
                        () -> new CheckApiException(ErrorCode.NOT_EXITS_USER)
                );

        if (refresh.equals(user.getRefreshToken())){

            String refreshToken = jwtUtil.createRefreshToken();

            user.updateRefreshToken(refreshToken);

            response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserEmail()));
            response.addHeader(JwtUtil.REFRESH_HEADER, refreshToken);

        } else {

            throw new CheckApiException(ErrorCode.NOT_MATCH_TOKEN);

        }

    }

}