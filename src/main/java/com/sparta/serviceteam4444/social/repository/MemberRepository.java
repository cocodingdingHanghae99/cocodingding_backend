package com.sparta.serviceteam4444.social.repository;

import com.sparta.serviceteam4444.social.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMembername(String membername);

    Optional<Member> findByKakaoId(Long id);

    Optional<Member> findByEmail(String email);
}
