package com.ourposapp.global.resolver.login;

import com.ourposapp.domain.user.constant.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoDto {

    private Long userId;
    private Role role;
}
