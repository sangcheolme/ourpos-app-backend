package com.ourposapp.api.service.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.api.controller.user.request.UserAddressRequest;
import com.ourposapp.api.controller.user.request.UserAddressUpdateRequest;
import com.ourposapp.api.controller.user.response.UserAddressResponse;
import com.ourposapp.domain.user.User;
import com.ourposapp.domain.user.UserAddress;
import com.ourposapp.domain.user.UserRepository;
import com.ourposapp.support.error.ErrorCode;
import com.ourposapp.support.error.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAddressService {

    private final UserRepository userRepository;

    @Transactional
    public void addUserAddress(Long userId, UserAddressRequest userAddressRequest) {
        User user = userRepository.findUserWithAddress(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));
        UserAddress userAddress = userAddressRequest.toEntity(user);
        user.addUserAddress(userAddress);
    }

    @Transactional
    public void updateUserAddress(Long userId, UserAddressUpdateRequest userAddressUpdateRequest) {
        User user = userRepository.findUserWithAddress(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));
        user.updateUserAddress(userAddressUpdateRequest);
    }

    @Transactional
    public void changeDefaultUserAddress(Long userId, Long newDefaultAddressId) {
        User user = userRepository.findUserWithAddress(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));
        user.changeDefaultUserAddress(newDefaultAddressId);
    }

    @Transactional
    public void deleteUserAddress(Long userId, Long userAddressId) {
        User user = userRepository.findUserWithAddress(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));
        user.deleteUserAddress(userAddressId);
    }

    public List<UserAddressResponse> findUserAddressesByUserId(Long userId) {
        List<UserAddress> userAddresses = userRepository.findUserAddresses(userId);
        return userAddresses.stream()
                .map(UserAddressResponse::of)
                .toList();
    }

    public UserAddressResponse findDefaultUserAddress(Long userId) {
        User user = userRepository.findUserWithAddress(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));
        UserAddress defaultUserAddress = user.getDefaultAddress();
        return UserAddressResponse.of(defaultUserAddress);
    }
}
