package com.smallsquare_plus.modules.user.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode {

    DUPLICATED_USERNAME("이미 존재하는 아이디입니다.", HttpStatus.CONFLICT);

    private final String message;
    private final HttpStatus status;
}
