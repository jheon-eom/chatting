package com.api.chatting.domain.user.dto;

public record LoginRequest(

        String loginId,

        String password
) {
}
