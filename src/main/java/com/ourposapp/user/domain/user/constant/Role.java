package com.ourposapp.user.domain.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN("관리자"), ROLE_USER("사용자"), ROLE_SUPER_ADMIN("슈퍼관리자"), ROLE_RIDER("라이더");

    private final String text;

    public static Role from(String role) {
        return Role.valueOf(role);
    }
}
