package com.ourposapp.user.application.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.AuthenticationException;
import com.ourposapp.user.domain.user.entity.User;
import com.ourposapp.user.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class UserRegisterService {

    private final UserRepository userRepository;

    public void register(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AuthenticationException(ErrorCode.USER_ALREADY_REGISTER);
        }
    }
}
