package com.smallsquare_plus.modules.post.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode {

    TITLE_WRONG_PATTERN("제목은 1 ~ 30자만 작성 가능합니다.", HttpStatus.BAD_REQUEST),

    CONTENT_WRONG_PATTERN("본문은 1 ~ 10000자만 작성 가능합니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
