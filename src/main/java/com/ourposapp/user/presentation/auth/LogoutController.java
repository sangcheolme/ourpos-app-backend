package com.ourposapp.user.presentation.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.global.response.Result;
import com.ourposapp.global.util.AuthorizationCookieUtils;
import com.ourposapp.user.application.auth.AuthenticationService;
import com.ourposapp.user.presentation.docs.LogoutControllerDocs;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LogoutController implements LogoutControllerDocs {

    private final AuthenticationService authenticationService;

    @PostMapping("/logout")
    public Result<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = AuthorizationCookieUtils.getAccessToken(request);
        authenticationService.logout(accessToken);

        ResponseCookie logoutAccessCookie = AuthorizationCookieUtils.createLogoutAccessToken();
        ResponseCookie logoutRefreshToken = AuthorizationCookieUtils.createLogoutRefreshToken();
        response.addHeader("Set-Cookie", logoutAccessCookie.toString());
        response.addHeader("Set-Cookie", logoutRefreshToken.toString());

        return Result.of("회원이 로그아웃 되었습니다.");
    }
}
