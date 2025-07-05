package com.smallsquare_plus.modules.user.web.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLogoutReqDTO {

    private String accessToken;
    private String refreshToken;
}
