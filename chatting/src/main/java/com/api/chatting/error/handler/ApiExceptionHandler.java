package com.api.chatting.error.handler;

import com.api.chatting.error.dto.ApiExceptionResponse;
import com.api.chatting.error.common.CustomApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ApiExceptionResponse apiExceptionResponse(CustomApiException e) {
        log.error("Exception to occur reason = {}", e.getReason());
        return new ApiExceptionResponse(e.getCode(), e.getReason());
    }
}
