package com.livear.LiveAR.repository;

import com.livear.LiveAR.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    User findByNickname(String nickname);
}
