package com.sparta.serviceteam4444.controller.user;

import com.sparta.serviceteam4444.dto.user.kakao.KakaoLoginRequestDto;
import com.sparta.serviceteam4444.dto.user.kakao.KakaoLoginResponseDto;
import com.sparta.serviceteam4444.service.user.KakaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Kakao"})
@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @ApiOperation(value = "인가코드 받아오기")
    @PostMapping("/kakao")
    public ResponseEntity<KakaoLoginResponseDto> kakaoLogin(@RequestBody KakaoLoginRequestDto kakaoLoginRequestDto){
//        return ResponseEntity.created(URI.create("/kakao")).body(kakaoService.kakaoLogin(kakaoLoginRequestDto));
        return null;
    }

}
