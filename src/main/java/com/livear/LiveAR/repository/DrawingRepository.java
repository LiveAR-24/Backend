package com.livear.LiveAR.repository;

import com.livear.LiveAR.domain.Drawing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DrawingRepository extends JpaRepository<Drawing, Long> {
    @Query("SELECT d FROM Drawing d JOIN DrawingLike dl ON d.id = dl.drawing.id WHERE dl.user.userId = :userId ORDER BY d.createdAt DESC")
    Page<Drawing> findDrawingsByUserId(Long userId, Pageable pageable);

    @Query("SELECT d FROM Drawing d " +
            "JOIN DrawingLike dl ON d = dl.drawing " +
            "GROUP BY d " +
            "ORDER BY COUNT(dl.drawing) DESC, MAX(d.createdAt) DESC")
    Page<Drawing> findPopularDrawings(Pageable pageable);

    @Query("select d from Drawing d where (:keyword is null or d.title like %:keyword%) ")
    Page<Drawing> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
