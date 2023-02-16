package com.sparta.serviceteam4444.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false, unique = true)
    private String userNickname;

    @Column(nullable = false)
    private String userPassword;

    public User(String userEmail, String userNickname, String userPassword) {
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userPassword = userPassword;
    }



//    @Column
//    private Long kakaoId;
//
//    @Column(nullable = false)
//    private String nickname;
//
//    @Column(nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Column(nullable = false)
//    private boolean state = true;
//
//    private String RefreshToken;
//
//    public User(String nickName, String email, String password) {
//        this.nickname = nickName;
//        this.email = email;
//        this.password = password;
//    }
//    public User(SignupRequestDto signupRequestDto, String password) {
//        this.nickname = signupRequestDto.getNickname();
//        this.email = signupRequestDto.getEmail();
//        this.password = password;
//    }
//    public User(String nickname, String email, Long kakaoId, String password) {
//        this.nickname = nickname;
//        this.email = email;
//        this.kakaoId = kakaoId;
//        this.password = password;
//    }
//    public User kakaoIdUpdate(Long kakaoId) {
//        this.kakaoId = kakaoId;
//        return this;
//    }
//    public void update(String npw) {
//        this.password=npw;
//    }
//
//    public void deleteUser() {
//        this.state = false;
//    }
}
