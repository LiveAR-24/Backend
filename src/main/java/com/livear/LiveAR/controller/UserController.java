package com.livear.LiveAR.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.livear.LiveAR.common.response.BaseResponse;
import com.livear.LiveAR.common.response.BaseResponseStatus;
import com.livear.LiveAR.dto.common.ErrorRes;
import com.livear.LiveAR.dto.user.UserReq;
import com.livear.LiveAR.security.dto.TokenResponseDto;
import com.livear.LiveAR.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@Tag(name = "user", description = "유저 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가입 & 로그인
     */
    @Operation(summary = "일반 사용자 회원가입 & 로그인", description = "일반 사용자 회원가입 & 로그인 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "일반 유저 회원가입 & 로그인 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "400", description = "비밀번호 오류", content = @Content(schema = @Schema(implementation = ErrorRes.class)))
    })
    @PostMapping(value = "/login")
    public BaseResponse<TokenResponseDto> signup (@Parameter(description = "일반 로그인/회원가입 요청 객체") @Valid @RequestBody UserReq.SignupAndLogin SignupAndLogin) {
        return BaseResponse.success(BaseResponseStatus.OK, userService.SignupAndLogin(SignupAndLogin));
    }

    /**
     * 카카오로그인
     */
    @Operation(summary = "소셜로그인", description = "카카오 로그인/회원가입 API 입니다.")
    @GetMapping(value = "/login/oauth2/code/kakao", produces = "application/json")
    public BaseResponse<TokenResponseDto> kakaoCallback(@RequestParam String code) throws JsonProcessingException {
        return BaseResponse.success(BaseResponseStatus.OK, userService.socialLogin(code));
    }

    /**
     * 닉네임 변경
     */
    @Operation(summary = "닉네임 변경", description = "닉네임 변경 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 변경 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 저장된 닉네임", content = @Content(schema = @Schema(implementation = ErrorRes.class)))
    })
    @PostMapping(value = "/nickname", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse changeNickname(@Parameter(description = "닉네임 변경 요청 객체") @Valid @RequestBody UserReq.ChangeNickname changeNickname ) {
        userService.changeNickname(changeNickname);
        return BaseResponse.success(BaseResponseStatus.OK);
    }

    /**
     * 회원 탈퇴
     */
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class)))
    })
    @DeleteMapping(value ="/user")
    @PreAuthorize("hasRole('USER')")
    public BaseResponse deleteUser() {
        userService.deleteUser();
        return BaseResponse.success(BaseResponseStatus.OK);
    }

    /**
     * AccessToken 재발급
     */
    @Operation(summary = "AccessToken 재발급", description = "만료 또는 새로고침으로 인한 AccessToken 재발급 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AccessToken 재발급 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 RefreshToken", content = @Content(schema = @Schema(implementation = ErrorRes.class)))
    })
    @PostMapping("/token")
    public BaseResponse<String> accessTokenRefresh(@Valid @RequestBody UserReq.Token request) {
        return BaseResponse.success(BaseResponseStatus.OK, userService.getAccessToken(request.getRefreshToken()));
    }
}
