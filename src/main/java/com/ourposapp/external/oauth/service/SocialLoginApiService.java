package com.ourposapp.external.oauth.service;

import com.ourposapp.external.oauth.model.OAuthAttributes;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);
}
