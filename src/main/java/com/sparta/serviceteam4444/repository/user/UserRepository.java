package com.sparta.serviceteam4444.repository.user;

import com.sparta.serviceteam4444.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String userEmail);


    Optional<User> findByUserNickname(String userNickname);
}