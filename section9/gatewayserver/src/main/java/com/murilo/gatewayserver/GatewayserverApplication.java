package com.murilo.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator italianoBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()
				.route(p -> p
						.path("/italiano/accounts/**")
						.filters(f -> f.rewritePath("/italiano/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Content-Type-Options", LocalDateTime.now().toString()))
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
