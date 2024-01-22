package com.livear.LiveAR.controller;

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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    /**
     * NAME: 회원가입
     */
    private final UserService userService;
    @Operation(summary = "일반 사용자 회원가입 (앱)", description = "일반 사용자 회원가입 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "일반 유저 회원가입 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 저장된 ID", content = @Content(schema = @Schema(implementation = ErrorRes.class)))
    })
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<Long> signup (@Parameter(description = "일반 회원가입 요청 객체") @RequestPart UserReq.Signup userSignup) {

        return BaseResponse.success(BaseResponseStatus.CREATED,
                userService.signup(userSignup));
    }

    /**
     * NAME: 로그인
     */
    @Operation(summary = "로그인", description = "사용자 로그인 API 입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = BaseResponse.class))),
            @ApiResponse(responseCode = "403", description = "회원가입 미승인, 탈퇴한 계정, 비밀번호 오류", content = @Content(schema = @Schema(implementation = ErrorRes.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원", content = @Content(schema = @Schema(implementation = ErrorRes.class)))
    })
    @PostMapping("/login")
    public BaseResponse<TokenResponseDto> login(@Parameter(description = "로그인 요청 객체") @Valid @RequestBody UserReq.Login userLogin){
        return BaseResponse.success(BaseResponseStatus.OK, userService.login(userLogin));
    }
}