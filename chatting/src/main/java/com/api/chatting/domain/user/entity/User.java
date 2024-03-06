package com.api.chatting.domain.user.entity;

import com.api.chatting.common.entity.BaseEntity;
import com.api.chatting.error.exception.UserAuthenticationException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user")
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "profile_image")
    private String profileImage;

    public static User createJoinUser(String loginId,
                                      String nickname,
                                      String password,
                                      String refreshToken,
                                      PasswordEncoder passwordEncoder) {
        User joinUser = new User();
        joinUser.loginId = loginId;
        joinUser.nickname = nickname;
        joinUser.refreshToken = refreshToken;
        joinUser.password = passwordEncoder.encode(password);
        return joinUser;
    }

    public void isMatchPassword(String loginPassword, PasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(loginPassword, this.password)) {
            throw new UserAuthenticationException();
        }
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
