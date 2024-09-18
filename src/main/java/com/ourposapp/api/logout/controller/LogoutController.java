package com.ourposapp.api.logout.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.api.logout.service.LogoutService;
import com.ourposapp.global.util.AuthorizationHeaderUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LogoutController implements LogoutControllerDocs {

    private final LogoutService logoutService;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorization) {
        AuthorizationHeaderUtils.validateAuthorization(authorization);
        String accessToken = authorization.split(" ")[1];

        logoutService.logout(accessToken);

        return ResponseEntity.ok("logout success");
    }
}
