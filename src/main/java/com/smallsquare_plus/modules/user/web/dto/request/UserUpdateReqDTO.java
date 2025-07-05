package com.smallsquare_plus.modules.user.web.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateReqDTO {

    private String username;
    private String name;
    private String email;
    private String nickname;

}
