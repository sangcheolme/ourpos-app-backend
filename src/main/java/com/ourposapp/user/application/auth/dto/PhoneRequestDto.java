package com.ourposapp.user.application.auth.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class PhoneRequestDto {

    @NotBlank(message = "전화번호는 필수 값입니다.")
    private String phoneNumber;
}
