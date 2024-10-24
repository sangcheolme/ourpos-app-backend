package com.ourposapp.api.controller.user;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.api.controller.user.request.UserAddressUpdateRequest;
import com.ourposapp.api.controller.user.response.UserAddressResponse;
import com.ourposapp.api.service.user.UserAddressService;
import com.ourposapp.support.login.Login;
import com.ourposapp.support.login.UserInfo;
import com.ourposapp.support.response.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users/addresses")
public class UserAddressController {

    private final UserAddressService userAddressService;

    @GetMapping
    public Result<List<UserAddressResponse>> getUserAddresses(@Login UserInfo userInfo) {
        Long userId = userInfo.getUserId();
        List<UserAddressResponse> userAddresses = userAddressService.findUserAddressesByUserId(userId);

        return Result.of(userAddresses, "회원 전체 주소 조회");
    }

    @GetMapping("/default")
    public Result<UserAddressResponse> getUserDefaultAddress(@Login UserInfo userInfo) {
        Long userId = userInfo.getUserId();
        UserAddressResponse defaultUserAddress = userAddressService.findDefaultUserAddress(userId);

        return Result.of(defaultUserAddress, "회원 기본 주소 조회");
    }

    @PutMapping
    public Result<Void> updateUserAddress(@Valid @RequestBody UserAddressUpdateRequest userAddressUpdateRequest,
                                          @Login UserInfo userInfo) {
        Long userId = userInfo.getUserId();
        userAddressService.updateUserAddress(userId, userAddressUpdateRequest);

        return Result.of("회원 주소 수정");
    }

    /**
     * 검증:
     * 로그인 회원의 주소인지
     * 주소가 기본 주소로 설정되어 있으면 삭제 불가
     */
    @DeleteMapping("/{userAddressId}")
    public Result<Void> deleteUserAddress(@PathVariable Long userAddressId, @Login UserInfo userInfo) {
        Long userId = userInfo.getUserId();
        userAddressService.deleteUserAddress(userId, userAddressId);

        return Result.of("회원 주소 삭제");
    }
}
