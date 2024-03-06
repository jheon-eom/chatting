package com.api.chatting.error.exception;

import com.api.chatting.error.common.CustomApiException;
import com.api.chatting.error.common.ErrorCode;

public class NicknameDuplicateException extends CustomApiException {

    public NicknameDuplicateException() {
        super(ErrorCode.V03.getCode(), ErrorCode.V03.getReason());
    }
}
