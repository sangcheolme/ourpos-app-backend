package com.ourposapp.user.presentation.auth;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String accessToken = AuthorizationCookieUtils.getAccessToken(request);
        authenticationService.logout(accessToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
