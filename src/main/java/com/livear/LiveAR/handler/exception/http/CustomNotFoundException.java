package com.livear.LiveAR.handler.exception.http;

import com.livear.LiveAR.handler.exception.CustomException;
import com.livear.LiveAR.handler.exception.ErrorCode;

public class CustomNotFoundException extends CustomException {
    public CustomNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
