package com.ourposapp.health.presentation;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.health.dto.HealthCheckResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class HealthCheckController implements HealthCheckControllerDocs {

    private final Environment environment;

    @Override
    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponseDto> healthCheck() {
        return ResponseEntity.ok(HealthCheckResponseDto.builder()
            .health("ok")
            .activeProfiles(Arrays.asList(environment.getActiveProfiles()))
            .build());
    }
}
