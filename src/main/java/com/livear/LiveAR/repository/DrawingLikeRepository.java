package com.livear.LiveAR.repository;

import com.livear.LiveAR.domain.Drawing;
import com.livear.LiveAR.domain.DrawingLike;
import com.livear.LiveAR.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawingLikeRepository extends JpaRepository<DrawingLike, Long> {
    boolean existsByUserAndDrawing(User user, Drawing drawing);
    void deleteByUserAndDrawing(User user, Drawing drawing);
}
