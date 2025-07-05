package com.smallsquare_plus.modules.user.web.dto.request;

import com.smallsquare_plus.modules.user.domain.enums.IsActive;
import com.smallsquare_plus.modules.user.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignupReqDTO {

    private String username;
    private String password;
    private String nickname;
    private String email;
    private String name;
}
