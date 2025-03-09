package com.vkr.gateway_service.config.routes;

import com.vkr.gateway_service.filter.AuthFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthFilter authFilter) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/profile", "/auth/**")
                        .filters(f -> f.filter(authFilter.apply((AuthFilter.Config) null)))
                        .uri("lb://auth-service")
                )
                .route("user-service", r -> r
                        .path("/users/**")
                        .filters(f -> f.filter(authFilter.apply((AuthFilter.Config) null)))
                        .uri("lb://user-service")
                )
                .build();
    }
}