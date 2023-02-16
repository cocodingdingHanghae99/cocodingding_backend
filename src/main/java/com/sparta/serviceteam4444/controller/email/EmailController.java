package com.sparta.serviceteam4444.controller.email;


import com.sparta.serviceteam4444.dto.user.ResponseDto;
import com.sparta.serviceteam4444.service.email.EmailService;
import com.sparta.serviceteam4444.entity.email.EmailMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value="/send-mail", produces = "application/json; charset=utf8")
@RestController
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

// 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
    @PostMapping("/email")
    public ResponseDto sendJoinMail(@RequestParam String email) {
        EmailMessage emailMessage = EmailMessage.builder()
                .to(email)
                .subject("똑똑똑")
                .build();

        return emailService.sendMail(emailMessage, "email");
    }
}