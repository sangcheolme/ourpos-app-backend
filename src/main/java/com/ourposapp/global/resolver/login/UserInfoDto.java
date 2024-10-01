package com.ourposapp.global.resolver.login;

import lombok.Builder;
import lombok.Getter;

import com.ourposapp.user.domain.user.constant.Role;

@Getter
@Builder
public class UserInfoDto {

    private Long userId;
    private Role role;
}
