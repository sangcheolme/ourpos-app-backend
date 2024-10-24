package com.ourposapp.api.controller.health;

import java.util.Arrays;

import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ourposapp.api.controller.health.response.HealthCheckResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "health check", description = "서버 상태 체크 API")
@RequiredArgsConstructor
@RestController
public class HealthCheckController {

    private final Environment environment;

    @Tag(name = "health check")
    @Operation(summary = "서버 Health Check API", description = "서버가 정상적으로 기동이 된 상태인지 검사하는 API")
    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponse> healthCheck() {
        return ResponseEntity.ok(HealthCheckResponse.builder()
                .health("ok")
                .activeProfiles(Arrays.asList(environment.getActiveProfiles()))
                .build());
    }
}
