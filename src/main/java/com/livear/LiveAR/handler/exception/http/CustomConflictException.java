package com.livear.LiveAR.handler.exception.http;

import com.livear.LiveAR.handler.exception.CustomException;
import com.livear.LiveAR.handler.exception.ErrorCode;

public class CustomConflictException extends CustomException {
    public CustomConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
