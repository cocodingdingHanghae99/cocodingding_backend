package com.sparta.serviceteam4444.entity.room;

import com.sparta.serviceteam4444.timestamp.Timestamp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Room extends Timestamp {

    // 방 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 방제
    @Column(nullable = false)
    private String roomTitle;

    // 최대 인원
    @Column(nullable = false)
    private Long maxMember;

    // 방장
    @Column(nullable = false)
    private String masterUser;

    // 방 상태
    @Column(nullable = false)
    private boolean status;

    // private일 때 사용할 비밀번호
    @Column
    private String password;

    // 현재 방 인원
    @Column
    private Long countUser;
}
