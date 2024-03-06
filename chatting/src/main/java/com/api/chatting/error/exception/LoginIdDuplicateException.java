package com.api.chatting.error.exception;

import com.api.chatting.error.common.CustomApiException;
import com.api.chatting.error.common.ErrorCode;

public class LoginIdDuplicateException extends CustomApiException {

    public LoginIdDuplicateException() {
        super(ErrorCode.V02.getCode(), ErrorCode.V02.getReason());
    }
}
