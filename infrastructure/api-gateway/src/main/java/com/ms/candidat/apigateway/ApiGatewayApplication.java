    package com.ms.candidat.apigateway;
    
    import org.springframework.boot.SpringApplication;
    import org.springframework.boot.autoconfigure.SpringBootApplication;
    import org.springframework.cloud.gateway.route.RouteLocator;
    import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
    import org.springframework.context.annotation.Bean;

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
                    .route("MSusers", r -> r.path("/api/**")
                            .uri("lb://node-login-service")) // Must match spring.application.name or Eureka registration name
                    .build();
        }
    }
