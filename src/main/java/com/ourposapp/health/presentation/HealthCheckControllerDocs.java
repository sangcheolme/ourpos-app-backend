package com.ourposapp.health.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.ourposapp.health.dto.HealthCheckResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "health check", description = "서버 상태 체크 API")
public interface HealthCheckControllerDocs {

    @Tag(name = "health check")
    @Operation(summary = "서버 Health Check API", description = "서버가 정상적으로 기동이 된 상태인지 검사하는 API")
    @GetMapping("/health")
    ResponseEntity<HealthCheckResponseDto> healthCheck();
}
