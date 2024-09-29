package com.ourposapp.user.presentation.user;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.global.resolver.login.Login;
import com.ourposapp.global.resolver.login.UserInfoDto;
import com.ourposapp.global.response.Result;
import com.ourposapp.user.application.user.UserAddressService;
import com.ourposapp.user.application.user.dto.UserAddressResponseDto;
import com.ourposapp.user.application.user.dto.UserAddressUpdateDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/addresses")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @GetMapping
    public Result<List<UserAddressResponseDto>> getUserAddresses(@Login UserInfoDto userInfoDto) {
        Long userId = userInfoDto.getUserId();
        List<UserAddressResponseDto> userAddresses = userAddressService.findUserAddressesByUserId(userId);

        return Result.of(userAddresses, "회원 전체 주소 데이터 조회");
    }

    @GetMapping("/default")
    public Result<UserAddressResponseDto> getUserDefaultAddress(@Login UserInfoDto userInfoDto) {
        Long userId = userInfoDto.getUserId();
        UserAddressResponseDto defaultUserAddress = userAddressService.findDefaultUserAddress(userId);

        return Result.of(defaultUserAddress, "회원 기본 주소 조회");
    }

    @PutMapping
    public Result<Void> updateUserAddress(@Valid @RequestBody UserAddressUpdateDto userAddressUpdateDto,
                                          @Login UserInfoDto userInfoDto) {
        Long userId = userInfoDto.getUserId();
        userAddressService.updateUserAddress(userId, userAddressUpdateDto);

        return Result.of("회원 주소 수정");
    }
}
