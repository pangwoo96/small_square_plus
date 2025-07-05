package com.smallsquare_plus.modules.user.applicaiton.impl;

import com.smallsquare_plus.modules.user.application.impl.UserServiceImpl;
import com.smallsquare_plus.modules.user.domain.entity.User;
import com.smallsquare_plus.modules.user.infrastructure.mapper.UserMapper;
import com.smallsquare_plus.modules.user.utils.UserUtils;
import com.smallsquare_plus.modules.user.web.dto.request.UserSignupReqDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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

    private UserSignupReqDTO createUserSignupReqDto() {
        return UserSignupReqDTO.builder()
                .username("testUsername")
                .password("testPassword!")
                .nickname("testNickname")
                .email("testEmail@naver.com")
                .name("testName")
                .build();
    }

    @Test
    @Order(1)
    void 회원가입_성공() {
        //given
        UserSignupReqDTO reqDTO = createUserSignupReqDto();

        given(passwordEncoder.encode(reqDTO.getPassword())).willReturn("secureTestPassword");
        given(userMapper.createUser(any(User.class))).willReturn(1L);

        //when
        Long savedUserId = userService.signup(reqDTO);

        //then
        assertThat(savedUserId).isEqualTo(1L);
    }


}
