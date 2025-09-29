    package com.ms.candidat.apigateway;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.cloud.gateway.route.RouteLocator;
    import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
    import org.springframework.context.annotation.Bean;
    import org.springframework.web.bind.annotation.RequestMapping;

    @SpringBootApplication
    public class ApiGatewayApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(ApiGatewayApplication.class, args);
        }

        @Bean
        public RouteLocator getRoutesApiGateway(RouteLocatorBuilder builder) {
            return builder.routes()
                    .route("MSMeeting", r -> r.path("/webDis/web/Meeting/**")
                            .uri("lb://MsMeeting"))
                    .route("MSMeeting", r -> r.path("/webDis/api/users/**")
                            .uri("lb://MsMeeting"))// Must match the `spring.application.name` of the Meeting microservice
                    .route("UserJwt-home", r -> r
                            .path("/user/home")
                            .uri("http://localhost:8085"))
                    .route("UserJwt-admin", r -> r
                            .path("/admin/home")
                            .uri("http://localhost:8085"))
                    .route("UserJwt-auth", r -> r
                            .path("/api/auth/**")
                            .uri("http://localhost:8085"))
                    .route("UserJwt-auth", r -> r
                            .path("/banquier/**")
                            .uri("http://localhost:8085"))
                    .route("Credit-producat", r -> r
                            .path("/api/products/**")
                            .uri("http://localhost:8094"))
                    .route("signature", r -> r
                            .path("/api/signatures/**")
                            .uri("http://localhost:8086"))
                    .route("loan-attach", r -> r
                            .path("/api/loan-requests/**")
                            .uri("http://localhost:8094"))
                    .route("credit-type", r -> r
                            .path("/api/credit-types/**")
                            .uri("http://localhost:8094"))






                    .build();
        }
    }
