package com.smallsquare_plus.modules.post.exception.exception;

import com.smallsquare_plus.modules.post.exception.errorcode.PostErrorCode;
import com.smallsquare_plus.modules.user.exception.errorcode.UserErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException {

  private final PostErrorCode errorCode;

  public PostException(PostErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
