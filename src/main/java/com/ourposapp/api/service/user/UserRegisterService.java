package com.ourposapp.api.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.domain.user.User;
import com.ourposapp.domain.user.UserRepository;
import com.ourposapp.support.error.ErrorCode;
import com.ourposapp.support.error.exception.AuthenticationException;

import lombok.RequiredArgsConstructor;

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
