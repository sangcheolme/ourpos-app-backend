package com.ourposapp.support.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

import com.ourposapp.support.interceptor.AdminAuthorizationInterceptor;
import com.ourposapp.support.interceptor.AuthorizationInterceptor;
import com.ourposapp.support.jwt.service.TokenManager;
import com.ourposapp.support.login.UserInfoArgumentResolver;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${client.base-url}")
    private String baseUrl;

    private final TokenManager tokenManager;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedOrigins(baseUrl)
                .allowCredentials(true)
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name(),
                        HttpMethod.OPTIONS.name()
                );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new AuthorizationInterceptor(tokenManager))
                .order(1)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns(
                        "/api/v1/access-token/issue",
                        "/api/v1/logout"
                );

        registry
                .addInterceptor(new AdminAuthorizationInterceptor(tokenManager))
                .order(2)
                .addPathPatterns("/api/v1/admin/**");

        // registry
        //     .addInterceptor(new CheckProfileCompleteInterceptor(tokenManager))
        //     .order(3)
        //     .addPathPatterns("/api/v1/**")
        //     .excludePathPatterns(
        //         "/api/v1/logout",
        //         "/api/v1/phone-auth",
        //         "/api/v1/phone-auth/check"
        //     );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserInfoArgumentResolver(tokenManager));
    }
}
