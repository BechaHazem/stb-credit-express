package com.ms.candidat.userjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.ms.candidat.userjwt")
public class UserJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserJwtApplication.class, args);
    }

}
