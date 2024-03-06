package com.api.chatting.error.exception;

import com.api.chatting.error.common.CustomApiException;
import com.api.chatting.error.common.ErrorCode;

public class ApiServerException extends CustomApiException {

    public ApiServerException() {
        super(ErrorCode.S01.getCode(), ErrorCode.S01.getReason());
    }
}
