package com.ourposapp.user.application.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ourposapp.global.error.ErrorCode;
import com.ourposapp.global.error.exception.EntityNotFoundException;
import com.ourposapp.user.application.user.dto.UserAddressRequestDto;
import com.ourposapp.user.application.user.dto.UserAddressResponseDto;
import com.ourposapp.user.application.user.dto.UserAddressUpdateDto;
import com.ourposapp.user.domain.user.entity.User;
import com.ourposapp.user.domain.user.entity.UserAddress;
import com.ourposapp.user.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserAddressService {

    private final UserRepository userRepository;

    @Transactional
    public void addUserAddress(Long userId, UserAddressRequestDto userAddressRequestDto) {
        User user = userRepository.findUserWithAddress(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));
        UserAddress userAddress = userAddressRequestDto.toEntity(user);
        user.addUserAddress(userAddress);
    }

    @Transactional
    public void updateUserAddress(Long userId, UserAddressUpdateDto userAddressUpdateDto) {
        User user = userRepository.findUserWithAddress(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));
        user.updateUserAddress(userAddressUpdateDto);
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

    public List<UserAddressResponseDto> findUserAddressesByUserId(Long userId) {
        List<UserAddress> userAddresses = userRepository.findUserAddresses(userId);
        return userAddresses.stream()
                .map(UserAddressResponseDto::of)
                .toList();
    }

    public UserAddressResponseDto findDefaultUserAddress(Long userId) {
        User user = userRepository.findUserWithAddress(userId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_EXIST));
        UserAddress defaultUserAddress = user.getDefaultAddress();
        return UserAddressResponseDto.of(defaultUserAddress);
    }
}
