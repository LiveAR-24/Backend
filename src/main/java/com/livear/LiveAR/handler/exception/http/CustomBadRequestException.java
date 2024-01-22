package com.livear.LiveAR.handler.exception.http;

import com.livear.LiveAR.handler.exception.CustomException;
import com.livear.LiveAR.handler.exception.ErrorCode;

public class CustomBadRequestException extends CustomException {
    public CustomBadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
