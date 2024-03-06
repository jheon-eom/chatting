package com.api.chatting.domain.user.dto;

public record JoinRequest(

        String loginId,

        String password,

        String nickname
) {
}
