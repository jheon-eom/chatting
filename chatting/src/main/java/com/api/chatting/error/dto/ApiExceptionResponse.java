package com.api.chatting.error.dto;

import com.api.chatting.error.common.CustomApiException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ApiExceptionResponse {

    private String code;

    private String reason;

    public static ApiExceptionResponse of(CustomApiException exception) {
        return new ApiExceptionResponse(exception.getCode(), exception.getReason());
    }
}
