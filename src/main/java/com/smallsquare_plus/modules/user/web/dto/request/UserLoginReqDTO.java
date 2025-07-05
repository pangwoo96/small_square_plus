package com.smallsquare_plus.modules.user.web.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginReqDTO {

    private String username;
    private String password;
}
