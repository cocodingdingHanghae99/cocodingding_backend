package com.sparta.serviceteam4444.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.serviceteam4444.dto.user.*;
import com.sparta.serviceteam4444.security.user.UserDetailsImpl;
import com.sparta.serviceteam4444.service.user.KakaoService;
import com.sparta.serviceteam4444.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = {"Users"})
@RestController
@RequestMapping(value="/user", produces = "application/json; charset=utf8")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

    @ApiOperation(value = "회원가입", notes = "유저 하나를 추가한다.")
    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto);
    }
    @ApiOperation(value = "카카오 로그인", notes = "이것은 카카오 로그인 버튼을 누름을 통해서 수행된다.")
    @GetMapping(value="/kakao")
    public ResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        return kakaoService.kakaoLogin(code, response);
    }
    @ApiOperation(value = "로그인", notes = "입력받은 정보를 기반으로 로그인 작업을 수행한다.")
    @RequestMapping(value="/login" , method = {RequestMethod.GET, RequestMethod.POST})
    public UserInfoDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
    @ApiOperation(value = "로그인 유저 이름 반환", notes = "로그인 한 유저가 메인페이지를 요청할 때 유저의 이름 반환한다.")
    @GetMapping("/info")
    public UserInfoDto getUserName(HttpServletRequest request, HttpServletResponse response) {
        return userService.getInfo(request, response);
    }
    @ApiOperation(value = "닉네임 변경", notes = "사용자의 닉네임를 변경한다.")
    @PatchMapping("/changenn")
    public ResponseDto changeNickname(@RequestBody UserInfoDto userinfoDto, HttpServletRequest request, HttpServletResponse response) {
        return userService.changeNickname(userinfoDto, request, response);
    }
    @ApiOperation(value = "비밀번호 변경", notes = "사용자의 비밀번호를 변경한다.")
    @PatchMapping("/changepw")
    public ResponseDto changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto, HttpServletRequest request, HttpServletResponse response) {
        return userService.changePassword(changePasswordRequestDto, request, response);
    }
    @ApiOperation(value = "계정 삭제", notes = "유저를 삭제한다.(자신 한정)")
    @DeleteMapping("/delete/{nickname}")
    public ResponseDto deleteBoard(@PathVariable String nickname,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.softDeleteId(nickname, userDetails.getUser());
    }
}
