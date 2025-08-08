package com.sieng.bank.gatewayserver;

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
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/siengbank/account/**")
                .filters(f -> f.rewritePath("/siengbank/account/(?<segment>.*)",
                                "/${segment}")
                .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString()))
                .uri("lb://ACCOUNT"))

                .route(p -> p.path("/siengbank/loan/**")
                .filters(f -> f.rewritePath("/siengbank/loan/(?<segment>.*)"
                        , "/${segment}"))
                .uri("lb://LOAN"))

                .route(p -> p.path("/siengbank/card/**")
                .filters(f -> f.rewritePath("/siengbank/card/(?<segment>.*)"
                        , "/${segment}"))
                .uri("lb://CARD")).build();
    }

}
