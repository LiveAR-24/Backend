package com.livear.LiveAR.handler.exception;

import com.livear.LiveAR.dto.common.ErrorRes;
import com.livear.LiveAR.handler.exception.http.CustomBadRequestException;
import com.livear.LiveAR.handler.exception.http.CustomConflictException;
import com.livear.LiveAR.handler.exception.http.CustomForbiddenException;
import com.livear.LiveAR.handler.exception.http.CustomNotFoundException;
import com.livear.LiveAR.security.TokenProvider;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import static com.livear.LiveAR.handler.exception.ErrorCode.*;

/**
 * NAME: CustomExceptionHandler 를 이용해 예외처리
 */

@RequiredArgsConstructor
@RestControllerAdvice
public class CustomExceptionHandler {
    private final TokenProvider tokenProvider;

    /* request @Valid 유효성 체크 통과하지 못할 시 실행됨 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRes> handleValidException(MethodArgumentNotValidException ex) {

        return new ResponseEntity<>(new ErrorRes(-1, BAD_REQUEST.getStatus(), ex.getBindingResult().getFieldError().getDefaultMessage()), HttpStatus.valueOf(400));
    }

    /* request @Valid 유효성 체크 통과하지 못할 시 실행됨 */
    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ErrorRes> handleValidException(ValidationException ex) {
        return new ResponseEntity<>(new ErrorRes(-1, FAILED_VALIDATION.getStatus(), FAILED_VALIDATION.getMessage()), HttpStatus.valueOf(400));
    }

    /* 필수 request 오지 않을 때 실행됨 */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorRes> handleRequiredRequest(MissingServletRequestPartException ex) {
        return new ResponseEntity<>(new ErrorRes(-1, 400, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 (잘못된 요청)
     */
    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<ErrorRes> badRequestExceptionHandler(CustomBadRequestException e) {
        return new ResponseEntity<>(new ErrorRes(-1, e.getErrorCode().getStatus(), e.getErrorCode().getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * 403 (권한없음)
     */
    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseEntity<ErrorRes> forbiddenExceptionHandler(CustomForbiddenException e) {
        return new ResponseEntity<>(new ErrorRes(-1, e.getErrorCode().getStatus(), e.getErrorCode().getMessage()), HttpStatus.FORBIDDEN);
    }

    /**
     * 404 (Not Found)
     */
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ErrorRes> notFoundExceptionHandler(CustomNotFoundException e) {
        return new ResponseEntity<>(new ErrorRes(-1, e.getErrorCode().getStatus(), e.getErrorCode().getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * 409 (충돌)
     */
    @ExceptionHandler(CustomConflictException.class)
    public ResponseEntity<ErrorRes> conflictExceptionHandler(CustomConflictException e) {
        return new ResponseEntity<>(new ErrorRes(-1, e.getErrorCode().getStatus(), e.getErrorCode().getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * 서버 500 내부 에러
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorRes> ServerExceptionHandler(Exception ex) {
        return new ResponseEntity<>(new ErrorRes(-1, INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
