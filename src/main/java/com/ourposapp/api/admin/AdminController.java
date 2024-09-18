package com.ourposapp.api.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.global.resolver.login.UserInfoDto;
import com.ourposapp.global.resolver.login.Login;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @GetMapping
    public ResponseEntity<UserInfoDto> adminTest(@Login UserInfoDto userInfoDto) {
        return ResponseEntity.ok(userInfoDto);
    }
}
