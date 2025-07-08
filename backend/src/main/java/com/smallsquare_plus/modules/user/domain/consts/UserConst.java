package com.smallsquare_plus.modules.user.domain.consts;

import lombok.Getter;

@Getter
public class UserConst {

    // username 정규식: 영문자와 숫자만 허용, 길이 6~20자
    public static final String USERNAME_REGEX = "^[A-Za-z\\d]{6,20}$";

    // password 정규식: 8자 이상, 영문자/숫자/특수문자 포함 필수
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";

    // email 정규식: 이메일 형식
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    // nickname 정규식: 최소 3글자, 최대 15글자
    public static final String NICKNAME_REGEX = "^[A-Za-z0-9]{3,15}$";

    // name 정규식: 한글과 영어 혼용 금지, 최대 30자까지 작성 가능
    public static final String KOREAN_NAME_REGEX = "^[가-힣]{1,30}$";
    public static final String ENGLISH_NAME_REGEX = "^[a-zA-Z]{1,30}$";
}
