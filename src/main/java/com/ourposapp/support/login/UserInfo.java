package com.ourposapp.support.login;

import com.ourposapp.domain.user.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfo {

    private Long userId;
    private Role role;
}
