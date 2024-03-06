package com.api.chatting.domain.user.service;

import com.api.chatting.domain.user.entity.User;
import com.api.chatting.domain.user.repository.UserReadRepository;
import com.api.chatting.error.exception.ApiServerException;
import com.api.chatting.error.exception.UserAuthenticationException;
import com.api.chatting.error.exception.UserNotFoundException;
import com.api.chatting.jwt.dto.JwtDto;
import com.api.chatting.jwt.dto.JwtRequest;
import com.api.chatting.jwt.util.JwtUtil;
import com.api.chatting.security.dto.CustomUserDetails;
import com.api.chatting.domain.user.dto.JoinRequest;
import com.api.chatting.domain.user.dto.LoginRequest;
import com.api.chatting.domain.user.repository.UserWriteRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserAuthService {

    private final UserValidateService userValidateService;

    private final UserReadRepository userReadRepository;

    private final UserWriteRepository userWriteRepository;

    private final JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder;

    public JwtDto join(JoinRequest joinRequest) {
        userValidateService.loginIdCheck(joinRequest.loginId());
        userValidateService.nicknameCheck(joinRequest.nickname());

        JwtDto jwtDto = jwtUtil.generateToken(CustomUserDetails.builder()
                .loginId(joinRequest.loginId())
                .build());

        User joinUser = User.createJoinUser(
                joinRequest.loginId(),
                joinRequest.password(),
                joinRequest.nickname(),
                jwtDto.refreshToken(),
                passwordEncoder
        );

        userWriteRepository.save(joinUser);

        return jwtDto;
    }

    public JwtDto login(LoginRequest loginRequest) {
        userValidateService.loginParameterCheck(loginRequest.loginId(), loginRequest.password());

        User loginUser = userReadRepository.findByLoginId(loginRequest.loginId())
                .orElseThrow(() -> new UserNotFoundException());

        loginUser.isMatchPassword(loginRequest.password(), passwordEncoder);

        JwtDto jwtDto = jwtUtil.generateToken(CustomUserDetails.builder()
                .loginId(loginUser.getLoginId())
                .build());

        return JwtDto.builder()
                .accessToken(jwtDto.accessToken())
                .accessToken(loginUser.getRefreshToken())
                .grantType(jwtDto.grantType())
                .authorizationType(jwtDto.authorizationType())
                .build();
    }

    @Transactional
    public JwtDto refreshToken(JwtRequest jwtRequest, HttpServletResponse response) {
        try {
            if (jwtUtil.verifyToken(jwtRequest.refreshToken(), response)) {
                User user = userReadRepository.findByRefreshToken(jwtRequest.refreshToken())
                        .orElseThrow(() -> new UserNotFoundException());

                JwtDto jwtDto = jwtUtil.generateToken(CustomUserDetails.builder()
                        .loginId(user.getLoginId())
                        .build());

                user.setRefreshToken(jwtDto.refreshToken());

                return jwtDto;
            } else {
                throw new UserAuthenticationException();
            }
        } catch (IOException e) {
            throw new ApiServerException();
        }
    }
}
