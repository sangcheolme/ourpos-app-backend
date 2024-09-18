package com.ourposapp.global.config.web;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ourposapp.global.interceptor.AdminAuthorizationInterceptor;
import com.ourposapp.global.interceptor.AuthorizationInterceptor;
import com.ourposapp.global.interceptor.CheckProfileCompleteInterceptor;
import com.ourposapp.global.jwt.service.TokenManager;
import com.ourposapp.global.resolver.login.UserInfoArgumentResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TokenManager tokenManager;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
            .allowedOrigins("http://localhost:3000")
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

        registry
            .addInterceptor(new CheckProfileCompleteInterceptor(tokenManager))
            .order(3)
            .addPathPatterns("/api/v1/**")
            .excludePathPatterns(
                "/api/v1/access-token/issue",
                "/api/v1/logout"
            );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserInfoArgumentResolver(tokenManager));
    }
}
