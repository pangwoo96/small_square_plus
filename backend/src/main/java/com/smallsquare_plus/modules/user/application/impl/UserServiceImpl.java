package com.smallsquare_plus.modules.user.application.impl;

import com.smallsquare_plus.modules.user.application.UserService;
import com.smallsquare_plus.modules.user.domain.entity.User;
import com.smallsquare_plus.modules.user.domain.enums.IsActive;
import com.smallsquare_plus.modules.user.domain.enums.Role;
import com.smallsquare_plus.modules.user.infrastructure.jwt.JwtProvider;
import com.smallsquare_plus.modules.user.infrastructure.jwt.JwtUtil;
import com.smallsquare_plus.modules.user.infrastructure.mapper.UserMapper;
import com.smallsquare_plus.modules.user.infrastructure.redis.RedisService;
import com.smallsquare_plus.modules.user.utils.UserUtils;
import com.smallsquare_plus.modules.user.web.dto.request.*;
import com.smallsquare_plus.modules.user.web.dto.response.RefreshTokenResDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserInfoResDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserLoginResDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserUpdateResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserUtils userUtils;
    private final JwtProvider jwtProvider;
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @Override
    @Transactional
    public Long signup(UserSignupReqDTO reqDTO) {

        // 1. username, password, nickname, email, name 검증
        userUtils.validateUsername(reqDTO.getUsername());
        userUtils.validatePassword(reqDTO.getPassword());
        userUtils.validateNickname(reqDTO.getNickname());
        userUtils.validateEmail(reqDTO.getEmail());
        userUtils.validateName(reqDTO.getName());

        // 2. User 객체
        User user = User.builder()
                .username(reqDTO.getUsername())
                .password(passwordEncoder.encode(reqDTO.getPassword()))
                .nickname(reqDTO.getNickname())
                .email(reqDTO.getEmail())
                .name(reqDTO.getName())
                .isActive(IsActive.ACTIVE)
                .role(Role.USER)
                .build();

        // 3. User 객체 저장 및 반환
        return userMapper.createUser(user);
    }

    @Override
    @Transactional
    public UserLoginResDTO login(UserLoginReqDTO reqDTO) {

        // 1. username & password 일치 여부 확인
        userUtils.validateLogin(reqDTO.getUsername(), reqDTO.getPassword());
        Long userId = userMapper.getUserIdByUsername(reqDTO.getUsername());
        Role role = userMapper.getRoleByUserId(userId);

        // 2. 토큰 생성
        String accessToken= jwtProvider.createAccessToken(userId, reqDTO.getUsername(), role);
        String refreshToken = jwtProvider.createRefreshToken(userId, reqDTO.getUsername(), role);

        // 3. 반환
        return UserLoginResDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public void logout(UserLogoutReqDTO reqDTO) {

        // 1. 토큰 추출
        String accessToken = reqDTO.getAccessToken();
        String refreshToken = reqDTO.getRefreshToken();

        // 2. 토큰 남은 유효시간을 추출
        long remainTimeAccessToken = jwtUtil.getRemainingExpirationMillis(accessToken);
        long remainTimeRefreshToken = jwtUtil.getRemainingExpirationMillis(refreshToken);

        // 3. RedisService에서 Role에 블랙리스트 저장
        redisService.saveBlacklist(accessToken, remainTimeAccessToken, refreshToken, remainTimeRefreshToken);
    }

    @Override
    public UserInfoResDTO me(Long userId) {

        // 1. User 객체 생성 및 검증
        User user = userMapper.getUserInfoByUserId(userId);
        userUtils.validateUserIsActive(user.getIsActive());

        // 2. 반환
        return UserInfoResDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .name(user.getName())
                .isActive(user.getIsActive())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

    }

    @Override
    public UserUpdateResDTO updateMe(Long userId, UserUpdateReqDTO reqDTO) {

        // 1. Request 값 검증
        userUtils.validateUsername(reqDTO.getUsername());
        userUtils.validateNickname(reqDTO.getNickname());
        userUtils.validateEmail(reqDTO.getEmail());
        userUtils.validateName(reqDTO.getName());

        // 2. 정보 수정
        userMapper.updateUserInfo(userId, reqDTO);

        // 3. 수정된 User 재조회
        User newUser = userMapper.getUserInfoByUserId(userId);

        // 4. 반환
        return UserUpdateResDTO.builder()
                .userId(newUser.getUserId())
                .username(newUser.getUsername())
                .nickname(newUser.getNickname())
                .email(newUser.getEmail())
                .name(newUser.getName())
                .isActive(newUser.getIsActive())
                .role(newUser.getRole())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public void deleteMe(Long userId, UserLogoutReqDTO reqDTO) {

        // 1. is_active = "INACTIVE" update
        int updatedRow = userMapper.deleteMe(userId);

        // 2. 업데이트 호출 검증
        userUtils.validateDeleteMe(updatedRow);

        // 3. logout 호출
        logout(reqDTO);
    }

    @Override
    @Transactional
    public RefreshTokenResDTO refreshToken(Long userId, RefreshTokenReqDTO reqDTO) {

        // 1. 토큰 검증
        jwtUtil.validateToken(reqDTO.getRefreshToken());
        jwtUtil.validateRefreshTokenBlackList(reqDTO.getRefreshToken());

        // 2. 유저 검증
        User user= userMapper.getUserInfoByUserId(userId);
        userUtils.validateUserIsActive(user.getIsActive());

        // 3. 블랙리스트
        redisService.saveRefreshBlackList(reqDTO.getRefreshToken(), jwtUtil.getRemainingExpirationMillis(reqDTO.getRefreshToken()));

        // 4. 토큰 생성
        String accessToken = jwtProvider.createAccessToken(user.getUserId(), user.getName(), user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId(), user.getName(), user.getRole());

        return RefreshTokenResDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
