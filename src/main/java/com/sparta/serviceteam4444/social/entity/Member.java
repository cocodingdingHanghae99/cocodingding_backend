package com.sparta.serviceteam4444.social.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.security.Timestamp;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column
    private Long kakaoIdTest;

    @Column
    private String kakaoProfileImgTest;

    @Column
    private String kakaoNicknameTest;

    @Column
    private String kakaoEmailTest;

    @Column
    private String userRoleTest;

//    @Column
//    @CreationTimestamp
//    private Timestamp createTime;
    //
    //
    //

    @Builder
    public Member(Long kakaoIdTest, String kakaoProfileImgTest, String kakaoNicknameTest,
                String kakaoEmailTest, String userRoleTest) {

        this.kakaoIdTest = kakaoIdTest;
        this.kakaoProfileImgTest = kakaoProfileImgTest;
        this.kakaoNicknameTest = kakaoNicknameTest;
        this.kakaoEmailTest = kakaoEmailTest;
        this.userRoleTest = userRoleTest;
    }
}
