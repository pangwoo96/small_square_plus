package com.smallsquare_plus.modules.user.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class RefreshTokenResDTO {

    private String accessToken;
    private String refreshToken;
}
