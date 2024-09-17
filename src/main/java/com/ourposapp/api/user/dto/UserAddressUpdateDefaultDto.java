package com.ourposapp.api.user.dto;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserAddressUpdateDefaultDto {

    @NotNull
    private Long UserId;

    @NotNull
    private Long UserAddressId;

    public UserAddressUpdateDefaultDto(Long userId, Long userAddressId) {
        UserId = userId;
        UserAddressId = userAddressId;
    }
}
