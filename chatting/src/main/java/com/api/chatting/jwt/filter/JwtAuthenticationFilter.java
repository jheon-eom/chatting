package com.api.chatting.jwt.filter;

import com.api.chatting.error.dto.ApiExceptionResponse;
import com.api.chatting.error.exception.UserAuthenticationException;
import com.api.chatting.jwt.util.JwtUtil;
import com.api.chatting.security.dto.CustomUserDetails;
import com.api.chatting.domain.user.entity.User;
import com.api.chatting.domain.user.repository.UserReadRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserReadRepository userReadRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");
        if (!StringUtils.hasText(accessToken)) {
            doFilter(request, response, filterChain);
            return;
        }
        if (jwtUtil.verifyToken(accessToken, response)) {
            Long userId = jwtUtil.extractUserId(accessToken);
            User user = userReadRepository.findById(userId).orElseThrow(() ->
                    new UserAuthenticationException());
            Authentication authentication = getAuthentication(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            ApiExceptionResponse apiExceptionResponse =
                    ApiExceptionResponse.of(new UserAuthenticationException());
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(apiExceptionResponse));
        }
    }

    private Authentication getAuthentication(User user) {
        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .loginId(user.getLoginId())
                .build();

        return new UsernamePasswordAuthenticationToken(customUserDetails, null);
    }
}

