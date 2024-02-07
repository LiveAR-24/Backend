package com.livear.LiveAR.service;

import com.livear.LiveAR.domain.Drawing;
import com.livear.LiveAR.domain.User;
import com.livear.LiveAR.domain.UserDrawing;
import com.livear.LiveAR.dto.userDrawing.UserDrawingReq;
import com.livear.LiveAR.dto.userDrawing.UserDrawingRes;
import com.livear.LiveAR.handler.exception.ErrorCode;
import com.livear.LiveAR.handler.exception.http.CustomForbiddenException;
import com.livear.LiveAR.handler.exception.http.CustomNotFoundException;
import com.livear.LiveAR.repository.DrawingRepository;
import com.livear.LiveAR.repository.UserDrawingRepository;
import com.livear.LiveAR.repository.UserRepository;
import com.livear.LiveAR.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDrawingService {
    private final S3Uploader s3Uploader;
    private final UserDrawingRepository userDrawingRepository;
    private final UserRepository userRepository;
    private final LoginService loginService;
    private final DrawingRepository drawingRepository;

    /**
     * 유저 그림 저장
     */
    public Long saveDrawing(UserDrawingReq.SaveDrawing saveDrawing, MultipartFile image) {
        Long userId = loginService.getLoginUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException(ErrorCode.NOT_FOUND_USER));
        Drawing drawing = drawingRepository.findById(saveDrawing.getDrawingId())
                .orElseThrow(() -> new CustomNotFoundException(ErrorCode.NOT_FOUND_DRAWING));

        String uploadFileUrl = s3Uploader.uploadFile(image);
        UserDrawing userDrawing = UserDrawing.builder()
                .user(user)
                .drawing(drawing)
                .imageUrl(uploadFileUrl)
                .build();
        userDrawingRepository.save(userDrawing);
        return userDrawing.getUserDrawingId();
    }

    /**
     * 유저 그림 삭제
     */
    @Transactional
    public void deleteDrawing(UserDrawingReq.DeleteDrawing deleteIdList) {
        Long userId = loginService.getLoginUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException(ErrorCode.NOT_FOUND_USER));

        for (Long userDrawingId : deleteIdList.getUserDrawingIdList()) {
            userDrawingRepository.findById(userDrawingId).ifPresent(userDrawing -> {
                if (!user.equals(userDrawing.getUser())){
                    throw new CustomForbiddenException(ErrorCode.FORBIDDEN);
                }
                userDrawingRepository.delete(userDrawing);
            });
        }
    }

    /**
     * 유저 그림 목록
     */
    public UserDrawingRes.Multiple listDrawing(Pageable pageable) {
        Long userId = loginService.getLoginUserId();
        Page<UserDrawing> userDrawingPage = userDrawingRepository.findUserDrawingsByUserUserId(userId, pageable);

        List<UserDrawingRes.Base> userDrawingList = userDrawingPage.getContent().stream()
                .map(userDrawing -> UserDrawingRes.Base.of(userDrawing))
                .collect(Collectors.toList());

        return UserDrawingRes.Multiple.of(userDrawingPage.getTotalElements(),userDrawingPage.getTotalPages(), userDrawingList);
    }
}
