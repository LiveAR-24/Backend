package com.livear.LiveAR.service;

import com.livear.LiveAR.domain.Drawing;
import com.livear.LiveAR.domain.DrawingLike;
import com.livear.LiveAR.domain.User;
import com.livear.LiveAR.dto.drawing.DrawingRes;
import com.livear.LiveAR.handler.exception.ErrorCode;
import com.livear.LiveAR.handler.exception.http.CustomNotFoundException;
import com.livear.LiveAR.repository.DrawingLikeRepository;
import com.livear.LiveAR.repository.DrawingRepository;
import com.livear.LiveAR.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DrawingService {
    private final UserRepository userRepository;
    private final LoginService loginService;
    private final DrawingLikeRepository drawingLikeRepository;
    private final DrawingRepository drawingRepository;


    /**
     * 도안 좋아요
     */
    @Transactional
    public Boolean likeDrawing(Long drawingId) {
        Long userId = loginService.getLoginUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException(ErrorCode.NOT_FOUND_USER));
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new CustomNotFoundException(ErrorCode.NOT_FOUND_DRAWING));

        boolean existLike = drawingLikeRepository.existsByUserAndDrawing(user, drawing);
        if(existLike){
            drawingLikeRepository.deleteByUserAndDrawing(user, drawing);
        }
        else{
            DrawingLike newLike = DrawingLike.builder()
                    .drawing(drawing)
                    .user(user)
                    .build();
            drawingLikeRepository.save(newLike);
        }
        return !existLike;
    }

    /**
     * 유저 도안 좋아요 목록
     */
    public DrawingRes.Multiple userLikeDrawingList(Pageable pageable) {
        Long userId = loginService.getLoginUserId();
        Page<Drawing> userLikeDrawingPage = drawingRepository.findDrawingsByUserId(userId, pageable);

        List<DrawingRes.Base> userLikeDrawingList = userLikeDrawingPage.getContent().stream()
                .map(drawing -> DrawingRes.Base.of(drawing, true))
                .collect(Collectors.toList());

        return DrawingRes.Multiple.of(userLikeDrawingPage.getTotalElements(), userLikeDrawingPage.getTotalPages(), userLikeDrawingList);
    }

    /**
     * 도안 인기순(좋아요순) 목록
     */
    public DrawingRes.Multiple likeDrawingList(Pageable pageable) {
        Long userId = loginService.getLoginUserId();
        Page<Drawing> likeDrawingPage = drawingRepository.findPopularDrawings(pageable);
        List<DrawingRes.Base> userDrawingList;

        if (userId.equals(0L)) {
            userDrawingList = likeDrawingPage.getContent().stream()
                    .map(drawing -> DrawingRes.Base.of(drawing, false))
                    .collect(Collectors.toList());
        } else {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomNotFoundException(ErrorCode.NOT_FOUND_USER));
            userDrawingList = likeDrawingPage.getContent().stream()
                    .map(drawing -> DrawingRes.Base.of(drawing, drawingLikeRepository.existsByUserAndDrawing(user, drawing)))
                    .collect(Collectors.toList());
        }
        return DrawingRes.Multiple.of(likeDrawingPage.getTotalElements(),likeDrawingPage.getTotalPages(), userDrawingList);
    }
}
