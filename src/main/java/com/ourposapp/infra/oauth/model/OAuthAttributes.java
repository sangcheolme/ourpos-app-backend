package com.ourposapp.infra.oauth.model;

import com.ourposapp.domain.user.LoginType;
import com.ourposapp.domain.user.Role;
import com.ourposapp.domain.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
