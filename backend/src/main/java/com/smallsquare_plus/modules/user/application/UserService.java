package com.smallsquare_plus.modules.user.application;

import com.smallsquare_plus.modules.user.web.dto.request.*;
import com.smallsquare_plus.modules.user.web.dto.response.RefreshTokenResDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserInfoResDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserLoginResDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserUpdateResDTO;

public interface UserService {

    Long signup(UserSignupReqDTO reqDTO);

    UserLoginResDTO login(UserLoginReqDTO reqDTO);

    void logout(UserLogoutReqDTO reqDTO);

    UserInfoResDTO me(Long userId);

    UserUpdateResDTO updateMe(Long userId, UserUpdateReqDTO reqDTO);

    void deleteMe(Long userId, UserLogoutReqDTO reqDTO);

    RefreshTokenResDTO refreshToken(Long userId, RefreshTokenReqDTO reqDTO);
}
