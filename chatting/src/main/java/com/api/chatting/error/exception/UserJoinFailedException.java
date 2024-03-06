package com.api.chatting.error.exception;

import com.api.chatting.error.common.CustomApiException;
import com.api.chatting.error.common.ErrorCode;

public class UserJoinFailedException extends CustomApiException {

    public UserJoinFailedException() {
        super(ErrorCode.U01.getCode(), ErrorCode.U01.getReason());
    }
}
