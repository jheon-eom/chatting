package com.api.chatting.jwt.util;

import com.api.chatting.error.dto.ApiExceptionResponse;
import com.api.chatting.error.exception.TokenExpiredException;
import com.api.chatting.jwt.dto.JwtDto;
import com.api.chatting.security.dto.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    private final Key secretKey;

    private static final long ACCESS_TOKEN_EXPIRATION_MILLIS = 1000L * 60L * 30L; // 30분

    private static final long REFRESH_TOKEN_EXPIRATION_MILLIS = 1000L * 60L * 60L * 24L * 7L; // 7일

    private static final String BEARER_TYPE = "Bearer";

    private static final String AUTHORIZATION_HEADER = "Authorization";

    public JwtUtil(@Value("${jwt.secret-key}") String secretKey) {
        String base64EncodedSecretKey = Encoders.BASE64.encode(
                secretKey.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64EncodedSecretKey));
    }

    public JwtDto generateToken(CustomUserDetails customUserDetails) {
        Date accessTokenExpiresIn = getTokenExpiration(ACCESS_TOKEN_EXPIRATION_MILLIS);
        Date refreshTokenExpiresIn = getTokenExpiration(REFRESH_TOKEN_EXPIRATION_MILLIS);

        Map<String, Object> claims = new HashMap<>();
        claims.put("loginId", customUserDetails.getLoginId());

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(accessTokenExpiresIn)
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(refreshTokenExpiresIn)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return JwtDto.builder()
                .grantType(BEARER_TYPE)
                .authorizationType(AUTHORIZATION_HEADER)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public boolean verifyToken(String token, HttpServletResponse response) throws IOException {
        try {
            parseClaims(token);
        } catch (ExpiredJwtException e) {
            log.info("Token is expired = {}", token);
            ObjectMapper objectMapper = new ObjectMapper();
            ApiExceptionResponse apiExceptionResponse =
                    ApiExceptionResponse.of(new TokenExpiredException());
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(apiExceptionResponse));
        } catch (UnsupportedJwtException |
                 MalformedJwtException |
                 SignatureException |
                 IllegalArgumentException e) {
            log.info("Token verify is failed = {}", e.getMessage());
            return false;
        }
        return true;
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Date getTokenExpiration(long expirationMillisecond) {
        Date date = new Date();
        return new Date(date.getTime() + expirationMillisecond);
    }

    public Long extractUserId(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);
    }
}
