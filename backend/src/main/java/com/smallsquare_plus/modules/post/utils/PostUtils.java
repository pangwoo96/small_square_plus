package com.smallsquare_plus.modules.post.utils;

import com.smallsquare_plus.modules.post.domain.consts.PostConst;
import com.smallsquare_plus.modules.post.exception.exception.PostException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.smallsquare_plus.modules.post.domain.consts.PostConst.CONTENT_REGEX;
import static com.smallsquare_plus.modules.post.domain.consts.PostConst.TITLE_REGEX;
import static com.smallsquare_plus.modules.post.exception.errorcode.PostErrorCode.CONTENT_WRONG_PATTERN;
import static com.smallsquare_plus.modules.post.exception.errorcode.PostErrorCode.TITLE_WRONG_PATTERN;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostUtils {

    public void validateTitle(String title) {
        if (!title.matches(TITLE_REGEX)) {
            log.info("title wrong pattern: {}", title);
            throw new PostException(TITLE_WRONG_PATTERN);
        }
    }

    public void validateContent(String content) {
        if (!content.matches(CONTENT_REGEX)) {
            log.info("content wrong pattern: {}", content);
            throw new PostException(CONTENT_WRONG_PATTERN);
        }
    }
}
