package com.livear.LiveAR.service;

import com.livear.LiveAR.domain.User;
import com.livear.LiveAR.dto.admin.AdminReq;
import com.livear.LiveAR.handler.exception.ErrorCode;
import com.livear.LiveAR.handler.exception.http.CustomConflictException;
import com.livear.LiveAR.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final S3Uploader s3Uploader;

    /**
     * 도안 등록
     */
    @Transactional
    public String createDrawing(AdminReq.CreateDrawing createDrawing, MultipartFile image) {
        String uploadFileUrl = s3Uploader.uploadFile(image);
        createDrawing.toEntity(uploadFileUrl);
        return "도면 생성 성공";
        }
}
