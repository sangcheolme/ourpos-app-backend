package com.ourposapp.api.health.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthCheckResponseDto {

    private String health;
    private List<String> activeProfiles;

    @Builder
    public HealthCheckResponseDto(String health, List<String> activeProfiles) {
        this.health = health;
        this.activeProfiles = activeProfiles;
    }
}
