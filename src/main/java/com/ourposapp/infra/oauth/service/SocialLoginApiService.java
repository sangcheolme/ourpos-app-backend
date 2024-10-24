package com.ourposapp.infra.oauth.service;

import com.ourposapp.infra.oauth.model.OAuthAttributes;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);
}
