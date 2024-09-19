package com.ourposapp.api.login.service;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.api.login.dto.PhoneAuth;
import com.ourposapp.api.login.repository.PhoneAuthRepository;
import com.ourposapp.domain.common.Phone;
import com.ourposapp.domain.user.entity.User;
import com.ourposapp.domain.user.service.UserService;
import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.AuthPhoneException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PhoneAuthService {

    private final UserService userService;
    private final PhoneAuthRepository phoneAuthRepository;
    private final PhoneAuthNotifier phoneAuthNotifier;

    public void verifyPhoneNumber(String phoneNumber, String authNumber, Long userId) {
        PhoneAuth phoneAuth = phoneAuthRepository.findById(phoneNumber).orElseThrow(
            () -> new AuthPhoneException(ErrorCode.PHONE_NUMBER_NOT_EXIST));

        String findAuthNumber = phoneAuth.getAuthNumber();

        if (!authNumber.equals(findAuthNumber)) {
            throw new AuthPhoneException(ErrorCode.INVALID_AUTH_NUMBER);
        }
        phoneAuthRepository.deleteById(phoneNumber);

        User user = userService.findUserById(userId);
        user.updatePhone(Phone.of(phoneNumber));
    }

    public void sendAuthNumber(String phoneNumber) {
        // 1. 인증 번호 생성 (랜덤 6자리 숫자)
        String authNumber = generateAuthNumber();

        // 2. PhoneAuth 객체 저장;
        phoneAuthRepository.save(PhoneAuth.of(phoneNumber, authNumber));

        // 3. 메시지 전송
        String message = "Your verification code is: " + authNumber;
        phoneAuthNotifier.sendOne(phoneNumber, message);
    }

    // 인증 번호 생성 메서드
    private String generateAuthNumber() {
        Random random = new Random();
        int authNumber = 100000 + random.nextInt(900000); // 6자리 랜덤 숫자
        return String.valueOf(authNumber);
    }
}
