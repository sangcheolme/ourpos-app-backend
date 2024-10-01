package com.ourposapp.external.oauth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import com.ourposapp.user.domain.user.constant.LoginType;
import com.ourposapp.user.domain.user.constant.Role;
import com.ourposapp.user.domain.user.entity.User;

@ToString
@Getter
@Builder
public class OAuthAttributes {

    private String username;
    private String nickname;
    private String profile;
    private LoginType loginType;

    public User toUserEntity(Role role) {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .loginType(loginType)
                .profile(profile)
                .role(role)
                .build();
    }

}
