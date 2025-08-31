package com.stb.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.stb.credit.dto.UserDTO;

@FeignClient(name = "UserJwt", url = "http://localhost:8085")
public interface UserClient {
    @GetMapping("/api/auth/by-agence")
    List<UserDTO> getUsersByAgence(@RequestParam("agence") String agence);
}