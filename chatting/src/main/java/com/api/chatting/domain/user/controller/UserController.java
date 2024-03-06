package com.api.chatting.domain.user.controller;

import com.api.chatting.common.dto.ApiResponse;
import com.api.chatting.jwt.dto.JwtDto;
import com.api.chatting.jwt.dto.JwtRequest;
import com.api.chatting.domain.user.dto.LoginRequest;
import com.api.chatting.domain.user.dto.JoinRequest;
import com.api.chatting.domain.user.service.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    private final UserAuthService userAuthService;

    @Description("회원가입")
    @PostMapping
    public ApiResponse<JwtDto> join(@RequestBody JoinRequest joinRequest) {
        JwtDto jwtDto = userAuthService.join(joinRequest);
        return new ApiResponse<>(HttpStatus.CREATED.value(), jwtDto);
    }

    @Description("로그인")
    @PostMapping("/login")
    public ApiResponse<JwtDto> login(@RequestBody LoginRequest loginRequest) {
        JwtDto jwtDto = userAuthService.login(loginRequest);
        return new ApiResponse<>(HttpStatus.OK.value(), jwtDto);
    }

    @Description("토큰 재발급")
    @PostMapping("/refresh-token")
    public ApiResponse<JwtDto> refreshToken(@RequestBody JwtRequest jwtRequest, HttpServletResponse response) {
        JwtDto jwtDto = userAuthService.refreshToken(jwtRequest, response);
        return new ApiResponse<>(HttpStatus.OK.value(), jwtDto);
    }
}
