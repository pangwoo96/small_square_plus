package com.smallsquare_plus.modules.user.utils;

import com.smallsquare_plus.modules.user.domain.enums.IsActive;
import com.smallsquare_plus.modules.user.exception.exception.UserException;
import com.smallsquare_plus.modules.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.smallsquare_plus.modules.user.domain.consts.UserConst.*;
import static com.smallsquare_plus.modules.user.domain.consts.UserConst.EMAIL_REGEX;
import static com.smallsquare_plus.modules.user.domain.consts.UserConst.ENGLISH_NAME_REGEX;
import static com.smallsquare_plus.modules.user.domain.consts.UserConst.KOREAN_NAME_REGEX;
import static com.smallsquare_plus.modules.user.exception.errorcode.UserErrorCode.*;
import static com.smallsquare_plus.modules.user.exception.errorcode.UserErrorCode.NAME_WRONG_PATTERN;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserUtils {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * username 검증 메소드
     * @param username
     */
    public void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            log.info("username not null : {}", username);
            throw new UserException(USERNAME_NOT_NULL);
        }
        if (!username.matches(USERNAME_REGEX)) {
            log.info("username wrong pattern : {}", username);
            throw new UserException(USERNAME_WRONG_PATTERN);
        }
        if (userMapper.existsByUsername(username)) {
            log.info("username already exist : {}", username);
            throw new UserException(USERNAME_ALREADY_EXISTS);
        }
    }

    /**
     * password 검증 메소드
     * @param password
     */
    public void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            log.info("password not null: {}", password);
            throw new UserException(PASSWORD_NOT_NULL);
        }
        if (!password.matches(PASSWORD_REGEX)) {
            log.info("password wrong pattern: {}", password);
            throw new UserException(PASSWORD_WRONG_PATTERN);
        }
    }

    /**
     * nickname 검증 메소드
     * @param nickname
     */
    public void validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            log.info("nickname not null: {}", nickname);
            throw new UserException(NICKNAME_NOT_NULL);
        }
        if (!nickname.matches(NICKNAME_REGEX)) {
            log.info("nickname wrong pattern: {}", nickname);
            throw new UserException(NICKNAME_WRONG_PATTERN);
        }
        if (userMapper.existsByNickname(nickname)) {
            log.info("nickname already exist: {}", nickname);
            throw new UserException(NICKNAME_ALREADY_EXISTS);
        }
    }

    /**
     * email 검증 메소드
     * @param email
     */
    public void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            log.info("email not null: {}", email);
            throw new UserException(EMAIL_NOT_NULL);
        }
        if (!email.matches(EMAIL_REGEX)) {
            log.info("email wrong pattern: {}", email);
            throw new UserException(EMAIL_WRONG_PATTERN);
        }
        if (userMapper.existsByEmail(email)) {
            log.info("email already exist: {}", email);
            throw new UserException(EMAIL_ALREADY_EXISTS);
        }
    }

    /**
     * name 검증 메소드
     * @param name
     */
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            log.info("name not null: {}", name);
            throw new UserException(NAME_NOT_NULL);
        }
        if (!name.matches(KOREAN_NAME_REGEX) && !name.matches(ENGLISH_NAME_REGEX)) {
            log.info("name wrong pattern: {}", name);
            throw new UserException(NAME_WRONG_PATTERN);
        }
    }

    public void validateLogin(String username, String password) {

        String savedPassword = userMapper.getPasswordByUsername(username);
        if (!passwordEncoder.matches(password, savedPassword)) {
            throw new UserException(PASSWORD_NOT_EQUAL);
        }
    }

    public void validateUserIsActive(IsActive isActive) {
        if (isActive == IsActive.INACTIVE) {
            log.info("isActive is inactive: {}", isActive);
            throw new UserException(USER_INACTIVE);
        }
    }

    public void validateDeleteMe(int updatedRow) {
        if (updatedRow == 0) {
            log.info("updated user failed");
            throw new UserException(UPDATE_USER_FAIL);
        }
    }
}
