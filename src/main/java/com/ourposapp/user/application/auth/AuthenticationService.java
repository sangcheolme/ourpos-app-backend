package com.ourposapp.user.application.auth;

import com.ourposapp.user.application.auth.dto.AuthTokenDto;
import com.ourposapp.user.domain.user.constant.LoginType;

public interface AuthenticationService {

    AuthTokenDto.Response authenticate(String accessToken, LoginType loginType);

    void logout(String accessToken);
}
