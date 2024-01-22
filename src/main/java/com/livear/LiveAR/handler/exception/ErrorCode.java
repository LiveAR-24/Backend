package com.livear.LiveAR.handler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * NAME: 에러 코드 정리
 */
@AllArgsConstructor
@Getter
public enum ErrorCode {
    //400 BAD_REQUEST 잘못된 요청
    BAD_REQUEST(400, "잘못된 요청입니다."),

    // 401 UNAUTHORIZED 권한없음(인증 실패)
    UNAUTHORIZED(401, "권한 인증에 실패했습니다."),

    // 403 FORBIDDEN 권한없음(인가 실패)
    INVALID_TOKEN(4031, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(4032, "만료된 토큰입니다."),
    INVALID_PASSWORD(4033, "올바르지 않은 비밀번호 입니다."),

    //404 NOT_FOUND 잘못된 리소스 접근
    NOT_FOUND_USER(40401, "존재하지 않는 회원입니다."),

    //409 CONFLICT 중복된 리소스
    ALREADY_SAVED_NICKNAME(4091, "이미 저장된 닉네임 입니다."),

    //500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(500, "서버 내부 에러입니다");

    private final int status;
    private final String message;
}
