package com.ms.candidat.client;

import com.ms.candidat.signaturemicroservice.Dto.UserDTO;
import com.ms.candidat.signaturemicroservice.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "UserJwt", url = "http://localhost:8093/api/auth" , configuration = FeignConfig.class)
public interface UserClient {
    @GetMapping("/profile")
    UserDTO getProfile(@RequestHeader("Cookie") String cookie); // returns logged-in user with customer details
}
