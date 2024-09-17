package com.ourposapp.api.health.dto;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HealthCheckResponseDto {

    @Schema(description = "서버 health 상태", example = "ok", requiredMode = REQUIRED)
    private String health;

    @Schema(description = "현재 실행중인 profile", example = "[dev, prod]", requiredMode = REQUIRED)
    private List<String> activeProfiles;

}
