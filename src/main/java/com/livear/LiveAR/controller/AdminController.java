package com.livear.LiveAR.controller;

import com.livear.LiveAR.common.response.BaseResponse;
import com.livear.LiveAR.common.response.BaseResponseStatus;
import com.livear.LiveAR.dto.admin.AdminReq;
import com.livear.LiveAR.service.AdminService;
import com.livear.LiveAR.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "admin", description = "관리자 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    /**
     * 관리자 도안 생성
     */
    @Operation(summary = "도안 등록", description = "관리자 도안 등록 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도안 등록 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping(value ="/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse<String> adminCreate(@RequestPart AdminReq.CreateDrawing createDrawing, @RequestPart MultipartFile image) {
        return BaseResponse.success(BaseResponseStatus.CREATED, adminService.createDrawing(createDrawing, image));
    }
}
