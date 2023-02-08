package com.sparta.serviceteam4444.social.repository;

import com.sparta.serviceteam4444.social.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByKakaoEmailTest(String kakaoEmailTest);

}
