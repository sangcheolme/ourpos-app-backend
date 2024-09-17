package com.ourposapp.domain.user.constant;

import java.util.Arrays;
import java.util.List;

public enum LoginType {

    KAKAO, NAVER;

    public static LoginType from(String type) {
        return LoginType.valueOf(type.toUpperCase());
    }

    public static boolean isLoginType(String type) {
        List<LoginType> loginTypes = Arrays.stream(LoginType.values())
            .filter(loginType -> loginType.name().equals(type))
            .toList();

        return loginTypes.size() != 1;
    }
}
