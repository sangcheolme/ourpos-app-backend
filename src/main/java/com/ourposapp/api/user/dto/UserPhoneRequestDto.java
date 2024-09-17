package com.ourposapp.api.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPhoneRequestDto {

    private String phoneNumber;
}
