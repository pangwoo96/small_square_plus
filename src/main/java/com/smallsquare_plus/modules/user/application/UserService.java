package com.smallsquare_plus.modules.user.application;

import com.smallsquare_plus.modules.user.web.dto.request.UserLoginReqDTO;
import com.smallsquare_plus.modules.user.web.dto.request.UserLogoutReqDTO;
import com.smallsquare_plus.modules.user.web.dto.request.UserSignupReqDTO;
import com.smallsquare_plus.modules.user.web.dto.response.UserLoginResDTO;

public interface UserService {

    Long signup(UserSignupReqDTO reqDTO);

    UserLoginResDTO login(UserLoginReqDTO reqDTO);

    void logout(UserLogoutReqDTO reqDTO);
}
