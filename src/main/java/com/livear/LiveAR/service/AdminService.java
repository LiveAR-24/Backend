package com.livear.LiveAR.service;

import com.livear.LiveAR.domain.Drawing;
import com.livear.LiveAR.domain.User;
import com.livear.LiveAR.dto.admin.AdminReq;
import com.livear.LiveAR.handler.exception.ErrorCode;
import com.livear.LiveAR.handler.exception.http.CustomNotFoundException;
import com.livear.LiveAR.repository.DrawingRepository;
import com.livear.LiveAR.repository.UserRepository;
import com.livear.LiveAR.s3.S3Uploader;
import com.livear.LiveAR.security.TokenProvider;
import com.livear.LiveAR.security.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.livear.LiveAR.domain.UserRole.ROLE_ADMIN;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final S3Uploader s3Uploader;
    private final UserRepository userRepository;
    private final DrawingRepository drawingRepository;
    private final LoginService loginService;
    private final TokenProvider tokenProvider;

    /**
     * 도안 등록
     */
    public Long createDrawing(AdminReq.CreateDrawing createDrawing, MultipartFile image) {
        String uploadFileUrl = s3Uploader.uploadFile(image);
        Drawing drawing = createDrawing.toEntity(uploadFileUrl);
        drawingRepository.save(drawing);
        return drawing.getDrawingId();
    }

    /**
     * 도안 삭제
     */
    public String deleteDrawing(Long drawingId) {
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new CustomNotFoundException(ErrorCode.NOT_FOUND_DRAWING));
        s3Uploader.deleteFile(drawing.getImageUrl());
        drawingRepository.delete(drawing);
        return "도안 삭제 성공";
    }

    /**
     * 관리자로 권한 변경
     */
    @Transactional
    public TokenResponseDto getAdmin() {
        Long userId = loginService.getLoginUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomNotFoundException(ErrorCode.NOT_FOUND_USER));
        user.changeRole(ROLE_ADMIN);
        TokenResponseDto tokenResponseDto = tokenProvider.generateTokenResponse(user);
        return tokenResponseDto;

    }
}
