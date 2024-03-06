package com.api.chatting.error.exception;

import com.api.chatting.error.common.CustomApiException;
import com.api.chatting.error.common.ErrorCode;

public class RequiredParameterException extends CustomApiException {

    public RequiredParameterException(String parameter) {
        super(ErrorCode.V01.getCode(), parameter + ErrorCode.V01.getReason());
    }
}
