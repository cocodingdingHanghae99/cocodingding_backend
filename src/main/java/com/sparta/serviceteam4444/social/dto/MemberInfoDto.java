package com.sparta.serviceteam4444.social.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoDto {
    private Long id;
    private String email;
    private String nicknmae;

    public MemberInfoDto(Long id, String nicknmae, String email) {
        this.id = id;
        this.nicknmae = nicknmae;
        this.email = email;
    }
}