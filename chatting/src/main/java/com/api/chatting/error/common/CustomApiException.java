package com.api.chatting.error.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomApiException extends RuntimeException {

    private final String code;

    private final String reason;
}
