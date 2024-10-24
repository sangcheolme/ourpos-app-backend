package com.ourposapp.api.controller.user.response;

import com.ourposapp.domain.user.Role;
import com.ourposapp.domain.user.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {

    private Long id;
    private String username;
    private String nickname;
    private String phoneNumber;
    private String profile;
    private Role role;

    public static UserInfoResponse of(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhone() != null ? user.getPhone().getPhoneNumber() : null)
                .role(user.getRole())
                .profile(user.getProfile())
                .build();
    }
}
