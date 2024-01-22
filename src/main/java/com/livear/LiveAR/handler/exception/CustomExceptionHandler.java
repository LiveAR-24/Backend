package com.livear.LiveAR.handler.exception;

import com.livear.LiveAR.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * NAME: CustomExceptionHandler 를 이용해 예외처리
 */

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class CustomExceptionHandler {
    private final TokenProvider tokenProvider;
}
