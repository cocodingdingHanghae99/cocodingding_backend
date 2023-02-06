package com.sparta.serviceteam4444.repository;

import com.sparta.serviceteam4444.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long id);

    Optional<User> findByNickname(String nickname);
}