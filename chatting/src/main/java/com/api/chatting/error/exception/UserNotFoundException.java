package com.api.chatting.error.exception;

import com.api.chatting.error.common.CustomApiException;
import com.api.chatting.error.common.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundException extends CustomApiException {

    public UserNotFoundException() {
        super(ErrorCode.U02.getCode(), ErrorCode.U02.getReason());
    }
}
