package com.livear.LiveAR.handler.exception.token;

import com.livear.LiveAR.handler.exception.CustomException;
import com.livear.LiveAR.handler.exception.ErrorCode;

public class ExpiredJwtTokenException extends CustomException {
    public ExpiredJwtTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
