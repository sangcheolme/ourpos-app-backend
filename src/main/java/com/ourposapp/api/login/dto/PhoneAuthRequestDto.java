package com.ourposapp.api.login.dto;

import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;

@Getter
@RedisHash(value = "phoneAuth", timeToLive = 300)
public class PhoneAuthRequestDto {

    @Id
    @NotBlank(message = "휴대폰 번호는 필수 값입니다.")
    private String phoneNumber;

    @NotBlank(message = "인증번호는 필수 값입니다.")
    private String authNumber;
}
