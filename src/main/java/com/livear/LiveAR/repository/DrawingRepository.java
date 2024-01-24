package com.livear.LiveAR.repository;

import com.livear.LiveAR.domain.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawingRepository extends JpaRepository<Drawing, Long> {
}
