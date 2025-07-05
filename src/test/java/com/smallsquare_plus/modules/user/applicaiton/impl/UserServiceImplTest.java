package com.smallsquare_plus.modules.user.applicaiton.impl;

import com.smallsquare_plus.modules.user.application.impl.UserServiceImpl;
import com.smallsquare_plus.modules.user.domain.entity.User;
import com.smallsquare_plus.modules.user.domain.enums.IsActive;
import com.smallsquare_plus.modules.user.domain.enums.Role;
import com.smallsquare_plus.modules.user.infrastructure.jwt.JwtProvider;
import com.smallsquare_plus.modules.user.infrastructure.jwt.JwtUtil;
import com.smallsquare_plus.modules.user.infrastructure.mapper.UserMapper;
import com.smallsquare_plus.modules.user.infrastructure.redis.RedisService;
import com.smallsquare_plus.modules.user.utils.UserUtils;
import com.smallsquare_plus.modules.user.web.dto.request.UserLoginReqDTO;
import com.smallsquare_plus.modules.user.web.dto.request.UserLogoutReqDTO;
import com.smallsquare_plus.modules.user.web.dto.request.UserSignupReqDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserInfoResDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserLoginResDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserUtils userUtils;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private RedisService redisService;

    @Test
    @Order(1)
    void 회원가입_성공() {
        //given
        UserSignupReqDTO reqDTO = UserSignupReqDTO.builder()
                .username("testUsername")
                .password("testPassword!")
                .nickname("testNickname")
                .email("testEmail@naver.com")
                .name("testName")
                .build();

        given(passwordEncoder.encode(reqDTO.getPassword())).willReturn("secureTestPassword");
        given(userMapper.createUser(any(User.class))).willReturn(1L);

        //when
        Long savedUserId = userService.signup(reqDTO);

        //then
        verify(userUtils).validateUsername(reqDTO.getUsername());
        verify(userUtils).validatePassword(reqDTO.getPassword());
        verify(userUtils).validateNickname(reqDTO.getNickname());
        verify(userUtils).validateEmail(reqDTO.getEmail());
        verify(userUtils).validateName(reqDTO.getName());

        assertThat(savedUserId).isEqualTo(1L);
    }

    @Test
    @Order(2)
    void 로그인_성공() {

        //given
        UserLoginReqDTO reqDTO = UserLoginReqDTO.builder()
                .username("testUsername")
                .password("testPassword!")
                .build();

        given(userMapper.getUserIdByUsername(reqDTO.getUsername())).willReturn(1L);
        given(userMapper.getRoleByUserId(1L)).willReturn(Role.USER);
        given(jwtProvider.createAccessToken(1L, reqDTO.getUsername(), Role.USER)).willReturn("testAccessToken");
        given(jwtProvider.createRefreshToken(1L, reqDTO.getUsername(), Role.USER)).willReturn("testRefreshToken");

        //when
        UserLoginResDTO resDTO = userService.login(reqDTO);

        //then
        verify(userUtils).validateLogin(reqDTO.getUsername(), reqDTO.getPassword());

        assertThat(resDTO.getAccessToken()).isEqualTo("testAccessToken");
        assertThat(resDTO.getRefreshToken()).isEqualTo("testRefreshToken");
    }

    @Test
    @Order(3)
    void 로그아웃_성공() {

        //given
        UserLogoutReqDTO reqDTO = UserLogoutReqDTO.builder()
                .accessToken("testAccessToken")
                .refreshToken("testRefreshToken")
                .build();

        given(jwtUtil.getRemainingExpirationMillis(reqDTO.getAccessToken())).willReturn(100L);
        given(jwtUtil.getRemainingExpirationMillis(reqDTO.getRefreshToken())).willReturn(100L);

        //when
        userService.logout(reqDTO);

        //then
        verify(jwtUtil).getRemainingExpirationMillis("testAccessToken");
        verify(jwtUtil).getRemainingExpirationMillis("testRefreshToken");
        verify(redisService).saveBlacklist("testAccessToken", 100L, "testRefreshToken", 100L);
    }

    @Test
    @Order(4)
    void 내_정보_조회_성공() {
        // given
        long userId = 1L;
        User mockUser = User.builder()
                .userId(userId)
                .username("testUsername")
                .password("testPassword")
                .nickname("testNickname")
                .email("testEmail@example.com")
                .name("testName")
                .isActive(IsActive.ACTIVE)
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(userMapper.getUserInfoByUserId(userId)).willReturn(mockUser);

        // when
        UserInfoResDTO resDTO = userService.me(userId);

        // then
        verify(userMapper).getUserInfoByUserId(userId);
        verify(userUtils).validateUserIsActive(resDTO.getIsActive());

        assertThat(resDTO.getUserId()).isEqualTo(1L);
        assertThat(resDTO.getUsername()).isEqualTo("testUsername");
        assertThat(resDTO.getNickname()).isEqualTo("testNickname");
        assertThat(resDTO.getEmail()).isEqualTo("testEmail@example.com");
        assertThat(resDTO.getName()).isEqualTo("testName");
        assertThat(resDTO.getIsActive()).isEqualTo(IsActive.ACTIVE);
        assertThat(resDTO.getRole()).isEqualTo(Role.USER);
    }

}
