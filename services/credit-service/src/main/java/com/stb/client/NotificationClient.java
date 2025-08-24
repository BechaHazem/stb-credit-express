package com.stb.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.stb.credit.dto.EmailDTO;

@FeignClient(name = "notification-service", url = "http://localhost:8095") 
public interface NotificationClient {

    @PostMapping("/api/notifications/send-email")
    void sendEmail(@RequestBody EmailDTO emailRequest);
}