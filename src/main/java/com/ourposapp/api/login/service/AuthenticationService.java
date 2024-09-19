package com.ourposapp.api.login.service;

import com.ourposapp.api.login.dto.AuthTokenDto;
import com.ourposapp.domain.user.constant.LoginType;

public interface AuthenticationService {

    AuthTokenDto.Response authenticate(String accessToken, LoginType loginType);
}
