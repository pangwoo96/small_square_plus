package com.smallsquare_plus.modules.user.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode {

    USERNAME_NOT_NULL("아이디는 필수값입니다.", HttpStatus.BAD_REQUEST),
    USERNAME_WRONG_PATTERN("아이디는 영문자와 숫자만 가능하며, 6자 이상, 20자 이내만 가능합니다.", HttpStatus.BAD_REQUEST),
    USERNAME_ALREADY_EXISTS("이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST),

    PASSWORD_NOT_NULL("비밀번호는 필수값입니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_WRONG_PATTERN("비밀번호는 영문자, 숫자, 특수문자를 반드시 포함해야하며 8자 이상만 가능합니다.", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_EQUAL("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),

    NICKNAME_NOT_NULL("닉네임은 필수값입니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_WRONG_PATTERN("닉네임은 3글자 이상, 15글자 이내만 가능합니다.", HttpStatus.BAD_REQUEST),
    NICKNAME_ALREADY_EXISTS("이미 존재하는 닉네임입니다.", HttpStatus.BAD_REQUEST),

    EMAIL_NOT_NULL("이메일은 필수값입니다.", HttpStatus.BAD_REQUEST),
    EMAIL_WRONG_PATTERN("이메일 형식에 맞지 않습니다.", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS("이미 존재하는 이메일입니다.", HttpStatus.BAD_REQUEST),

    NAME_NOT_NULL("이름은 필수값입니다.", HttpStatus.BAD_REQUEST),
    NAME_WRONG_PATTERN("이름은 한글과 영어를 섞으면 안되고 최대 30자까지 작성 가능합니다.", HttpStatus.BAD_REQUEST),

    ACCESS_TOKEN_EXPIRED("Access Token이 만료되었습니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED("Refresh Token이 만료되었습니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
