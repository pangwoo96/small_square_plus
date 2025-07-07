package com.smallsquare_plus.modules.post.domain.consts;

import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class PostConst {

    // title 정규식: 1글자 ~ 30자까지 작성 가능
    public static final String TITLE_REGEX = "^.{1,30}$";

    // content: 1글자 ~ 10000자까지 작성 가능
    public static final String CONTENT_REGEX = "^.{1,10000}$";

}
