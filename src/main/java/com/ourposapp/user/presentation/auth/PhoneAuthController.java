package com.ourposapp.user.presentation.auth;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.ourposapp.global.resolver.login.Login;
import com.ourposapp.global.resolver.login.UserInfoDto;
import com.ourposapp.global.response.Result;
import com.ourposapp.user.application.auth.PhoneAuthService;
import com.ourposapp.user.application.auth.dto.PhoneAuthRequestDto;
import com.ourposapp.user.application.auth.dto.PhoneRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PhoneAuthController {

    private final PhoneAuthService phoneAuthService;

    @PostMapping("/phone-auth")
    public ResponseEntity<Result<Void>> authPhone(@Valid @RequestBody PhoneRequestDto phoneRequestDto) {
        phoneAuthService.sendAuthNumber(phoneRequestDto.getPhoneNumber());

        return ResponseEntity.ok(Result.of("핸드폰 인증번호를 요청하였습니다."));
    }

    @PostMapping("/phone-auth/check")
    public ResponseEntity<Result<Void>> authPhoneCheck(@Valid @RequestBody PhoneAuthRequestDto phoneAuthRequestDto,
                                                       @Login UserInfoDto userInfoDto) {
        String phoneNumber = phoneAuthRequestDto.getPhoneNumber();
        String authNumber = phoneAuthRequestDto.getAuthNumber();
        Long userId = userInfoDto.getUserId();
        phoneAuthService.verifyPhoneNumber(phoneNumber, authNumber, userId);

        return ResponseEntity.ok(Result.of("핸드폰 인증에 성공하였습니다."));
    }
}
