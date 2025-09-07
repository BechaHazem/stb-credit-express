package com.ms.candidat.signaturemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.ms.candidat.client")
public class SignatureMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignatureMicroserviceApplication.class, args);
    }

}
