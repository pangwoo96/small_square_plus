package com.smallsquare_plus.modules.user.application;

import com.smallsquare_plus.modules.user.web.dto.request.UserSignupReqDTO;

public interface UserService {

    Long signup(UserSignupReqDTO reqDTO);
}
