package com.sparta.serviceteam4444.entity;

import com.sparta.serviceteam4444.dto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long kakaoId;
    @Column(nullable = false)
    private String nickname;
    @Column
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER;
    @Column(nullable = false)
    private boolean state = true;
    public User(String nickName, String email, String password) {
        this.nickname = nickName;
        this.email = email;
        this.password = password;
    }
    public User(SignupRequestDto signupRequestDto, String password) {
        this.nickname = signupRequestDto.getNickname();
        this.email = signupRequestDto.getEmail();
        this.password = password;
    }
    public User(String nickname, String email, Long kakaoId, String password) {
        this.nickname = nickname;
        this.email = email;
        this.kakaoId = kakaoId;
        this.password = password;
    }
    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
    public void update(String npw) {
        this.password=npw;
    }

    public void deleteUser() {
        this.state = false;
    }
}
