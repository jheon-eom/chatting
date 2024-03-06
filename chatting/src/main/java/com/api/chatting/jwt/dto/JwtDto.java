package com.api.chatting.jwt.dto;

import lombok.Builder;

@Builder
public record JwtDto(

        String grantType,

        String authorizationType,

        String accessToken,

        String refreshToken
) {

}
