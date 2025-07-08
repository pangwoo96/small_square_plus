package com.smallsquare_plus.modules.user.web.dto.request;

import com.smallsquare_plus.modules.user.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtTokenReqDTO {

    private Long userId;
    private String username;
}
