package com.ourposapp.infra.oauth.service;

import com.ourposapp.domain.user.LoginType;

public interface AuthenticationService {

    AuthTokenDto.Response authenticate(String accessToken, LoginType loginType);

    void logout(String accessToken);

}
