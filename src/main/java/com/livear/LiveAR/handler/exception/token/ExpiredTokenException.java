package com.livear.LiveAR.handler.exception.token;

import com.livear.LiveAR.handler.exception.CustomException;
import com.livear.LiveAR.handler.exception.ErrorCode;

public class ExpiredTokenException extends CustomException {
    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
