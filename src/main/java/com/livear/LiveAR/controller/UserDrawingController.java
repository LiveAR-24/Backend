package com.livear.LiveAR.controller;

import com.livear.LiveAR.common.response.BaseResponse;
import com.livear.LiveAR.common.response.BaseResponseStatus;
import com.livear.LiveAR.dto.userDrawing.UserDrawingReq;
import com.livear.LiveAR.dto.userDrawing.UserDrawingRes;
import com.livear.LiveAR.service.UserDrawingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "myDrawing", description = "유저 그림 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/myDrawing")
public class UserDrawingController {
    private final UserDrawingService userDrawingService;

    /**
     * 유저 그림 저장하기
     */
    @Operation(summary = "유저 그림 저장하기", description = "유저 그림 저장 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 그림 저장 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @PostMapping(value ="/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<Long> saveUserDrawing(@Valid @RequestPart UserDrawingReq.SaveDrawing saveDrawingDto, @RequestPart MultipartFile drawingImage) {
        return BaseResponse.success(BaseResponseStatus.CREATED, userDrawingService.saveDrawing(saveDrawingDto, drawingImage));
    }

    /**
     * 유저 그림 삭제
     */
    @Operation(summary = "유저 그림 삭제", description = "유저 그림 삭제 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 그림 삭제 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @DeleteMapping(value ="")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<String> deleteUserDrawing(@Valid @RequestBody UserDrawingReq.DeleteDrawing userDrawingIdList) {
        userDrawingService.deleteDrawing(userDrawingIdList);
        return BaseResponse.success(BaseResponseStatus.OK);

    }

    /**
     * 유저 그림 목록
     */
    @Operation(summary = "유저 그림 목록", description = "유저 그림 최신순 목록 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 그림 최신순 목록 조회 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @GetMapping(value ="")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<UserDrawingRes.Multiple> userDrawingList(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
                                                             Pageable pageable) {
        return BaseResponse.success(BaseResponseStatus.OK, userDrawingService.listDrawing(pageable));
    }
}
