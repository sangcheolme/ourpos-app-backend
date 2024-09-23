package com.ourposapp.global.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ourposapp.global.resolver.login.Login;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPI30
@OpenAPIDefinition(
    info = @Info(
        title = "OURPOS",
        version = "1.0.0",
        description = "OURPOS 주문 관리 시스템 API"
    )
)
@Configuration
public class SwaggerConfig {

    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(Login.class);
    }

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
            .group("OURPOS-APP")
            .packagesToScan("com.ourposapp.api")
            .pathsToMatch("/api/v1/**")
            .pathsToExclude("/api/v1/admin/**")
            .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenAdminApi() {
        return GroupedOpenApi.builder()
            .group("OURPOS-ADMIN")
            .packagesToScan("com.ourposapp.api")
            .pathsToMatch("/api/v1/admin/**")
            .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList("Authorization"))
            .components(new Components()
                .addSecuritySchemes("Authorization",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
