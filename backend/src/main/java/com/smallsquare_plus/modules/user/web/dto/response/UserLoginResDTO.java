package com.smallsquare_plus.modules.user.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResDTO {

    private String accessToken;
    private String refreshToken;
}
