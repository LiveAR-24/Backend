package com.livear.LiveAR.handler.exception.http;

import com.livear.LiveAR.handler.exception.CustomException;
import com.livear.LiveAR.handler.exception.ErrorCode;

public class CustomForbiddenException extends CustomException {
    public CustomForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
