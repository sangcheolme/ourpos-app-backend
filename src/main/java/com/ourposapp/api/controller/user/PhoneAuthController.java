package com.ourposapp.api.controller.user;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.infra.phone.PhoneAuthRequest;
import com.ourposapp.infra.phone.PhoneAuthService;
import com.ourposapp.infra.phone.PhoneRequest;
import com.ourposapp.support.login.Login;
import com.ourposapp.support.login.UserInfo;
import com.ourposapp.support.response.Result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PhoneAuthController {

    private final PhoneAuthService phoneAuthService;

    @PostMapping("/phone-auth")
    public ResponseEntity<Result<Void>> authPhone(@Valid @RequestBody PhoneRequest phoneRequest) {
        phoneAuthService.sendAuthNumber(phoneRequest.getPhoneNumber());

        return ResponseEntity.ok(Result.of("핸드폰 인증번호를 요청하였습니다."));
    }

    @PostMapping("/phone-auth/check")
    public ResponseEntity<Result<Void>> authPhoneCheck(@Valid @RequestBody PhoneAuthRequest phoneAuthRequest,
                                                       @Login UserInfo userInfo) {
        String phoneNumber = phoneAuthRequest.getPhoneNumber();
        String authNumber = phoneAuthRequest.getAuthNumber();
        Long userId = userInfo.getUserId();
        phoneAuthService.verifyPhoneNumber(phoneNumber, authNumber, userId);

        return ResponseEntity.ok(Result.of("핸드폰 인증에 성공하였습니다."));
    }
}
