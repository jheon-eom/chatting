package com.api.chatting.error.exception;

import com.api.chatting.error.common.CustomApiException;
import com.api.chatting.error.common.ErrorCode;

public class TokenExpiredException extends CustomApiException {

    public TokenExpiredException() {
        super(ErrorCode.A02.getCode(), ErrorCode.A02.getReason());
    }
}
