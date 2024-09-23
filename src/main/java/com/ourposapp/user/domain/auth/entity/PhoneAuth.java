package com.ourposapp.user.domain.auth.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "phoneAuth", timeToLive = 300)
public class PhoneAuth {

    @Id
    private String phoneNumber;
    private String authNumber;

    public static PhoneAuth of(String phoneNumber, String authNumber) {
        return new PhoneAuth(phoneNumber, authNumber);
    }
}
