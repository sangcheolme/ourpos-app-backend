package com.ourposapp.global.resolver.login;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ourposapp.user.domain.user.constant.Role;
import com.ourposapp.global.jwt.service.TokenManager;
import com.ourposapp.global.util.CookieUtils;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenManager tokenManager;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
        boolean hasUserInfoType = UserInfoDto.class.isAssignableFrom(parameter.getParameterType());

        return hasLoginAnnotation && hasUserInfoType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
        String accessToken = CookieUtils.getAccessToken(request);

        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        Long userId = Long.valueOf((Integer)tokenClaims.get("userId"));
        String role = (String)tokenClaims.get("role");

        return UserInfoDto.builder()
            .userId(userId)
            .role(Role.from(role))
            .build();
    }
}
