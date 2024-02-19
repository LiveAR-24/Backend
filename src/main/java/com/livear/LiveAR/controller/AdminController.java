package com.livear.LiveAR.controller;

import com.livear.LiveAR.common.response.BaseResponse;
import com.livear.LiveAR.common.response.BaseResponseStatus;
import com.livear.LiveAR.dto.admin.AdminReq;
import com.livear.LiveAR.dto.common.ErrorRes;
import com.livear.LiveAR.security.dto.TokenResponseDto;
import com.livear.LiveAR.service.AdminService;
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
    public BaseResponse<Long> adminCreate(@Valid @RequestPart AdminReq.CreateDrawing createDrawingDto, @RequestPart MultipartFile drawingImage) {
        return BaseResponse.success(BaseResponseStatus.CREATED, adminService.createDrawing(createDrawingDto, drawingImage));
    }

    /**
     * 관리자 도안 삭제
     */
    @Operation(summary = "도안 삭제", description = "관리자 도안 삭제 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도안 삭제 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 도안에 접근", content = @Content(schema = @Schema(implementation = ErrorRes.class)))
    })
    @DeleteMapping(value ="/{drawingId}")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse adminDelete(@PathVariable Long drawingId) {
        adminService.deleteDrawing(drawingId);
        return BaseResponse.success(BaseResponseStatus.OK);

    }

    /**
     * 관리자로 역할 변경
     */
    @Operation(summary = "관리자로 권한 변경", description = "관리자로 권한 변경 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관리자로 권한 변경 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping(value ="")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<TokenResponseDto> getAdmin() {
        return BaseResponse.success(BaseResponseStatus.OK, adminService.getAdmin());
    }

    /**
     * 관리자 도안 제목 변경
     */
    @Operation(summary = "도안 제목 변경", description = "도안 제목 변경 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도안 제목 변경 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping(value = "/{drawingId}", produces = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse changeTitle(@PathVariable Long drawingId, @Valid @RequestBody AdminReq.CreateDrawing changeTitle ) {
        adminService.changeTitle(drawingId, changeTitle);
        return BaseResponse.success(BaseResponseStatus.OK);
    }
}
