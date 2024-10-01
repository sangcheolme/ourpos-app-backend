package com.ourposapp.user.application.user.dto;

import com.ourposapp.user.domain.user.constant.Role;
import com.ourposapp.user.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponseDto {

    private Long id;
    private String username;
    private String nickname;
    private String phoneNumber;
    private String profile;
    private Role role;

    public static UserInfoResponseDto of(User user) {
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhone() != null ? user.getPhone().getPhoneNumber() : null)
                .role(user.getRole())
                .profile(user.getProfile())
                .build();
    }
}
