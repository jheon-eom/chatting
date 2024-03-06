package com.api.chatting.error.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // 인증 에러
    A01("A01", "User authentication failed."),
    A02("A02", "Access token is expired."),

    // 유저 에러
    U01("U01", "User join is failed."),
    U02("U02", "User not found."),

    // 유효성 검사 에러
    V01("V01", " is required."),
    V02("V02", "Login id is duplicated."),
    V03("V03", "Nickname is duplicated."),

    // 서버 에러
    S01("S01", "Server error.")
    ;

    private final String code;

    private final String reason;
}
