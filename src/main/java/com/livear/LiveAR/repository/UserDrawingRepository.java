package com.livear.LiveAR.repository;

import com.livear.LiveAR.domain.UserDrawing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDrawingRepository extends JpaRepository<UserDrawing, Long> {
    Page<UserDrawing> findUserDrawingsByUserUserId(Long userId, Pageable pageable);
}
