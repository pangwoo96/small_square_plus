package com.smallsquare_plus.modules.user.application.impl;

import com.smallsquare_plus.modules.user.application.UserService;
import com.smallsquare_plus.modules.user.domain.entity.User;
import com.smallsquare_plus.modules.user.domain.enums.IsActive;
import com.smallsquare_plus.modules.user.domain.enums.Role;
import com.smallsquare_plus.modules.user.infrastructure.mapper.UserMapper;
import com.smallsquare_plus.modules.user.utils.UserUtils;
import com.smallsquare_plus.modules.user.web.dto.request.UserSignupReqDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserUtils userUtils;

    @Override
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
}
