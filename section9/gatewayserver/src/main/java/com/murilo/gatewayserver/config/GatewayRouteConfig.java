package com.murilo.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouteLocator italianoBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/italiano/accounts/**")
                        .filters(f -> f.rewritePath("/italiano/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Content-Type-Options", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport")))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/italiano/loans/**")
                        .filters(f -> f.rewritePath("/italiano/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Content-Type-Options", LocalDateTime.now().toString()))
                        .uri("lb://LOANS"))
                .route(p -> p
                        .path("/italiano/cards/**")
                        .filters(f -> f.rewritePath("/italiano/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Content-Type-Options", LocalDateTime.now().toString()))
                        .uri("lb://CARDS")).build();
    }
}