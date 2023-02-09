package com.sparta.serviceteam4444.social.entity;

import com.sparta.serviceteam4444.timestamp.Timestamp;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

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

    @Column
    @CreatedDate
    private String createTime;

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
