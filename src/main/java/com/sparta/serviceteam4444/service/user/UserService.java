package com.sparta.serviceteam4444.service.user;

import com.sparta.serviceteam4444.dto.user.*;
import com.sparta.serviceteam4444.entity.email.EmailMessage;
import com.sparta.serviceteam4444.entity.user.User;
import com.sparta.serviceteam4444.repository.user.UserRepository;

import com.sparta.serviceteam4444.jwt.JwtUtil;
import com.sparta.serviceteam4444.service.email.EmailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    String pt = "^[a-z\\\\d`~!@#$%^&*()-_=+]{4,10}$";
    String ptt = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,15}$";


    @Transactional
    public ResponseDto signup(SignupRequestDto signupRequestDto) {
        //이름, 비밀번호 대조를 위해 값을 뽑아놓음
        String email = signupRequestDto.getEmail();
        String pwcheck = signupRequestDto.getPassword();
        //비밀번호 조건 확인
        if (!Pattern.matches(ptt, pwcheck)) {
            throw new IllegalArgumentException(
                    "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 에 한해서 구성되어야합니다.");
        }
        // 회원 중복 확인
        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        //저장을 바로 하면 안되고 encoding해서 저장하기
        String password = passwordEncoder.encode(pwcheck);

        //등록등록
        User user = new User(signupRequestDto,password);
        userRepository.save(user);
        //이메일 보내기
        log.info("작동 완료");
        EmailMessage emailMessage = EmailMessage.builder()
                                                .to(email)
                                                .subject("똑똑똑")
                                                .build();
        emailService.sendMail(emailMessage, "email");
        return new ResponseDto("가입 완료");
    }


    @Transactional(readOnly = true)
    public UserInfoDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        //이름, 비밀번호 대조를 위해 값을 뽑아놓음
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        //토큰을 생성해서 유저에게 줌
        response.addHeader(JwtUtil.ACCESS_HEADER, jwtUtil.createAccessToken(user.getEmail()));
        response.addHeader(JwtUtil.REFRESH_HEADER, jwtUtil.createRefreshToken(user.getEmail()));
        return new UserInfoDto(user.getNickname(),user.getEmail());
    }
    @Transactional
    public UserInfoDto getInfo(HttpServletRequest request, HttpServletResponse response) {
        //토큰 검사
        Claims claims = tokenCheck(request);
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user =  userRepository.findByEmail(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        //토큰 재부여
        response.addHeader(JwtUtil.ACCESS_HEADER, jwtUtil.createAccessToken(user.getEmail()));
        response.addHeader(JwtUtil.REFRESH_HEADER, jwtUtil.createRefreshToken(user.getEmail()));
        return new UserInfoDto(user.getNickname(),user.getEmail());
    }
    @Transactional
    public ResponseDto changeNickname(UserInfoDto userinfoDto, HttpServletRequest request, HttpServletResponse response) {
        //토큰 검사
        Claims claims = tokenCheck(request);
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user =  userRepository.findByEmail(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        //요청받은 닉네임 값 확인
        String nnn = userinfoDto.getNickname();
        if (nnn==null) {
            throw new IllegalArgumentException(
                    "닉네임을 작성해주세요.");
        }
        //닉네임을 업데이트
        user.update(nnn);

        //토큰 재부여
        response.addHeader(JwtUtil.ACCESS_HEADER, jwtUtil.createAccessToken(user.getEmail()));
        response.addHeader(JwtUtil.REFRESH_HEADER, jwtUtil.createRefreshToken(user.getEmail()));

        return new ResponseDto("닉네임이 변경되었습니다");
    }
    @Transactional
    public ResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto, HttpServletRequest request, HttpServletResponse response) {
        //토큰 검사
        Claims claims = tokenCheck(request);
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user =  userRepository.findByEmail(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        //요청받은 비번 값 확인
        String npw = changePasswordRequestDto.getPassword();
        if (!Pattern.matches(ptt, npw)) {
            throw new IllegalArgumentException(
                    "비밀번호는 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자 에 한해서 구성되어야합니다.");
        }
        //저장을 바로 하면 안되고 encoding해서 저장하기
        String npww = passwordEncoder.encode(npw);
        user.update(npww);

        //토큰 재부여
        response.addHeader(JwtUtil.ACCESS_HEADER, jwtUtil.createAccessToken(user.getEmail()));
        response.addHeader(JwtUtil.REFRESH_HEADER, jwtUtil.createRefreshToken(user.getEmail()));
        return new ResponseDto("비밀번호가 변경되었습니다");
    }

    @Transactional  // soft delete
    public ResponseDto softDeleteId(HttpServletRequest request) {
        //토큰 검사
        Claims claims = tokenCheck(request);
        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        Optional<User> user =  userRepository.findByEmail(claims.getSubject());
        if (user.isEmpty() || !user.get().isState()) {    //삭제된 상태에서 다시 삭제 방지
            throw new IllegalArgumentException("사용자가 없습니다.");
        }
        // 삭제를 database -> state true->false (휴먼계정)
        user.get().deleteUser();
        return new ResponseDto("아이디 삭제 완료");
    }

    @Transactional
    public Claims tokenCheck(HttpServletRequest request){
        // 토큰에서 사용자 정보 가져오기
        Claims claims;
        try{
            // Request에서 Access Token 가져오기
            String token = jwtUtil.resolveAccessToken(request);
            claims= jwtUtil.getUserInfoFromToken(token);
        }catch (ExpiredJwtException e){
            String tokken = jwtUtil.resolveRefreshToken(request);
            claims = jwtUtil.getUserInfoFromToken(tokken);
        }
        return claims;
    }
}