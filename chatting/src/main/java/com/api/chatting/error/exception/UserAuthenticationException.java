package com.api.chatting.error.exception;

import com.api.chatting.error.common.CustomApiException;
import com.api.chatting.error.common.ErrorCode;

public class UserAuthenticationException extends CustomApiException {

    public UserAuthenticationException() {
        super(ErrorCode.A01.getCode(), ErrorCode.A01.getReason());
    }
}
