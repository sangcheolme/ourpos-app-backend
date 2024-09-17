package com.ourposapp.domain.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.api.user.validator.UserValidator;
import com.ourposapp.domain.common.Phone;
import com.ourposapp.domain.user.entity.User;
import com.ourposapp.domain.user.entity.UserAddress;
import com.ourposapp.domain.user.repository.UserRepository;
import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.AuthenticationException;
import com.ourposapp.global.error.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Transactional
    @Override
    public void register(User user) {
        validateDuplicateUser(user);
        userRepository.save(user);
    }

    private void validateDuplicateUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())
            || userRepository.existsByPhone(user.getPhone())) {
            throw new AuthenticationException(ErrorCode.USER_ALREADY_REGISTER);
        }
    }

    @Transactional
    @Override
    public void updatePhoneNumber(Long userId, String phoneNumber) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));

        user.updatePhone(Phone.of(phoneNumber));
    }

    @Transactional
    @Override
    public void changeDefaultUserAddress(Long userId, Long newDefaultAddressId) {
        User user = userRepository.findUserWithAddress(userId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));

        UserAddress newDefaultAddress = user.getUserAddress(newDefaultAddressId);
        UserAddress currentDefaultAddress = user.getDefaultAddress();

        userValidator.checkIfAlreadyDefaultAddress(newDefaultAddress);

        currentDefaultAddress.unsetDefault();
        newDefaultAddress.setAsDefault();
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByRefreshToken(String refreshToken) {
        User user = userRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        LocalDateTime tokenExpirationTime = user.getTokenExpirationTime();
        if (tokenExpirationTime.isBefore(LocalDateTime.now())) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        return user;
    }
}
