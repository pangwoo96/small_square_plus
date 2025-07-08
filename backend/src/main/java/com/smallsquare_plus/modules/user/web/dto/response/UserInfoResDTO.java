package com.smallsquare_plus.modules.user.web.dto.response;

import com.smallsquare_plus.modules.user.domain.enums.IsActive;
import com.smallsquare_plus.modules.user.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserInfoResDTO {

    private Long userId;
    private String username;
    private String nickname;
    private String email;
    private String name;
    private IsActive isActive;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
