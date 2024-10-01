package com.ourposapp.user.application.auth;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.ourposapp.common.model.Phone;
import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.AuthPhoneException;
import com.ourposapp.user.application.user.UserService;
import com.ourposapp.user.domain.auth.entity.PhoneAuth;
import com.ourposapp.user.domain.auth.repository.PhoneAuthRedisRepository;
import com.ourposapp.user.domain.user.entity.User;

@RequiredArgsConstructor
@Transactional
@Service
public class PhoneAuthService {

    private final UserService userService;
    private final PhoneAuthRedisRepository phoneAuthRedisRepository;
    private final PhoneAuthNotifier phoneAuthNotifier;

    public void verifyPhoneNumber(String phoneNumber, String authNumber, Long userId) {
        PhoneAuth phoneAuth = phoneAuthRedisRepository.findById(phoneNumber).orElseThrow(
                () -> new AuthPhoneException(ErrorCode.PHONE_NUMBER_NOT_EXIST));

        String findAuthNumber = phoneAuth.getAuthNumber();

        if (!authNumber.equals(findAuthNumber)) {
            throw new AuthPhoneException(ErrorCode.INVALID_AUTH_NUMBER);
        }
        phoneAuthRedisRepository.deleteById(phoneNumber);

        User user = userService.findUserById(userId);
        user.updatePhone(Phone.of(phoneNumber));
    }

    public void sendAuthNumber(String phoneNumber) {
        // 1. 인증 번호 생성 (랜덤 6자리 숫자)
        String authNumber = generateAuthNumber();

        // 2. PhoneAuth 객체 저장;
        phoneAuthRedisRepository.save(PhoneAuth.of(phoneNumber, authNumber));

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
