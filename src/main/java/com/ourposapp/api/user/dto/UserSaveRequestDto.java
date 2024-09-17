package com.ourposapp.api.user.dto;

import com.ourposapp.domain.common.Phone;
import com.ourposapp.domain.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String username;
    private String nickname;
    private Phone phone;

    public User toEntity() {
        return User.builder()
            .nickname(nickname)
            .phone(phone)
            .build();
    }
}
