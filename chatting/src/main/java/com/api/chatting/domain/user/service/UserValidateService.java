package com.api.chatting.domain.user.service;

import com.api.chatting.domain.user.repository.UserReadRepository;
import com.api.chatting.error.exception.LoginIdDuplicateException;
import com.api.chatting.error.exception.NicknameDuplicateException;
import com.api.chatting.error.exception.RequiredParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class UserValidateService {

    private final UserReadRepository userReadRepository;

    private static final String LOGIN_ID = "loginId";

    private static final String NICKNAME = "nickname";

    private static final String PASSWORD = "password";

    public void loginIdCheck(String loginId) {
        if (!StringUtils.hasText(loginId)) {
            throw new RequiredParameterException(LOGIN_ID);
        }
        userReadRepository.findByLoginId(loginId).ifPresent(user -> {
            throw new LoginIdDuplicateException();
        });
    }

    public void nicknameCheck(String nickname) {
        if (!StringUtils.hasText(nickname)) {
            throw new RequiredParameterException(NICKNAME);
        }
        userReadRepository.findByNickname(nickname).ifPresent(user -> {
            throw new NicknameDuplicateException();
        });
    }

    public void loginParameterCheck(String loginId, String password) {
        if (!StringUtils.hasText(loginId)) {
            throw new RequiredParameterException(LOGIN_ID);
        }

        if (!StringUtils.hasText(password)) {
            throw new RequiredParameterException(PASSWORD);
        }
    }
}
