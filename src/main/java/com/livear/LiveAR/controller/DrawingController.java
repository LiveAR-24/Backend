package com.livear.LiveAR.controller;

import com.livear.LiveAR.common.response.BaseResponse;
import com.livear.LiveAR.common.response.BaseResponseStatus;
import com.livear.LiveAR.dto.common.ErrorRes;
import com.livear.LiveAR.dto.drawing.DrawingRes;
import com.livear.LiveAR.service.DrawingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "drawing", description = "도안 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class DrawingController {
    private final DrawingService drawingLikeService;

    /**
     * 도안 좋아요
     */
    @Operation(summary = "도안 좋아요 클릭", description = "도안 좋아요 클릭 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도안 좋아요 반영 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 도안에 접근", content = @Content(schema = @Schema(implementation = ErrorRes.class)))
    })
    @PostMapping(value ="/like/{drawingId}")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<Boolean> likeDrawing(@PathVariable Long drawingId) {
        return BaseResponse.success(BaseResponseStatus.OK, drawingLikeService.likeDrawing(drawingId));
    }

    /**
     * 유저 도안 좋아요 목록
     */
    @Operation(summary = "유저 도안 좋아요 목록", description = "유저 도안 좋아요 목록 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 도안 좋아요 목록 조회 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @GetMapping(value ="/user/like")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse<DrawingRes.Multiple> userLikeDrawingList(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return BaseResponse.success(BaseResponseStatus.OK, drawingLikeService.userLikeDrawingList(pageable));
    }

    /**
     * 도안 인기순(좋아요순) 목록
     */
    @Operation(summary = "도안 인기순 목록", description = "도안 인기순 목록 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도안 인기순 목록 조회 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @GetMapping(value ="/like")
    public BaseResponse<DrawingRes.Multiple> likeDrawingList(@PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return BaseResponse.success(BaseResponseStatus.OK, drawingLikeService.likeDrawingList(pageable));
    }

    /**
     * 도안 최신순 목록 + 검색
     */
    @Operation(summary = "도안 최신순 목록", description = "도안 최신순 목록 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도안 최신순 목록 조회 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @GetMapping(value ="/date")
    public BaseResponse<DrawingRes.Multiple> dateDrawingList(@RequestParam(required = false) String keyword,
                                                             @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC)
                                                             Pageable pageable) {
        return BaseResponse.success(BaseResponseStatus.OK, drawingLikeService.dateDrawingList(keyword, pageable));
    }

    /**
     * 도안 상세보기
     */
    @Operation(summary = "도안 상세보기", description = "도안 상세보기 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도안 상세보기 조회 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 도안에 접근", content = @Content(schema = @Schema(implementation = ErrorRes.class)))
    })
    @GetMapping(value ="/detail/{drawingId}")
    public BaseResponse<DrawingRes.Base> detailDrawing(@PathVariable Long drawingId) {
        return BaseResponse.success(BaseResponseStatus.OK, drawingLikeService.detailDrawing(drawingId));
    }
}
