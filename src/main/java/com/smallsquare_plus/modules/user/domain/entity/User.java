package com.smallsquare_plus.modules.user.domain.entity;

import com.smallsquare_plus.modules.user.domain.enums.IsActive;
import com.smallsquare_plus.modules.user.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {

    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String name;
    private IsActive isActive;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
public class User {

}
