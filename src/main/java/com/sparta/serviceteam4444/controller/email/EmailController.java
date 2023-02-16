package com.sparta.serviceteam4444.controller.email;

import com.sparta.serviceteam4444.dto.email.EmailPostDto;
import com.sparta.serviceteam4444.dto.email.EmailResponseDto;
import com.sparta.serviceteam4444.service.email.EmailService;
import com.sparta.serviceteam4444.entity.email.EmailMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value="/send-mail", produces = "application/json; charset=utf8")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    // 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email")
    public ResponseEntity sendJoinMail(@RequestBody EmailPostDto emailPostDto) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(emailPostDto.getEmail())
                .subject("똑똑똑")
                .build();

        String code = emailService.sendMail(emailMessage, "email");

        EmailResponseDto emailResponseDto = new EmailResponseDto();
        emailResponseDto.setCode(code);

        return ResponseEntity.ok(emailResponseDto);
    }
}