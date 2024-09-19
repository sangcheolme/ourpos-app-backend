package com.ourposapp.api.login.service;

import com.ourposapp.api.login.dto.LoginDto;
import com.ourposapp.domain.user.constant.LoginType;

public interface AuthenticationService {

    LoginDto.Response authenticate(String accessToken, LoginType loginType);
}
