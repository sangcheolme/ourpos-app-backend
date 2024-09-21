package com.ourposapp.api.login.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.api.login.service.AuthenticationService;
import com.ourposapp.global.util.AuthorizationHeaderUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LogoutController implements LogoutControllerDocs {

    private final AuthenticationService authenticationService;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorization) {
        AuthorizationHeaderUtils.validateAuthorization(authorization);
        String accessToken = authorization.split(" ")[1];

        authenticationService.logout(accessToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
