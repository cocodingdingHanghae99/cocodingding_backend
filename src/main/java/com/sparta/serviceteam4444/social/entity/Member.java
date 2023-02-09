package com.sparta.serviceteam4444.social.entity;

import com.sparta.serviceteam4444.entity.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    private String membername;

    private Long kakaoId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public Member(String membername, String password, String email, UserRoleEnum role) {
        this.membername = membername;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Member(String membername, Long kakaoId, String password, String email, UserRoleEnum role) {
        this.membername = membername;
        this.kakaoId = kakaoId;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Member kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}