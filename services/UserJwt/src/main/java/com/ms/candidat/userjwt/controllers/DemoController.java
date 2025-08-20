package com.ms.candidat.userjwt.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/admin/home")
    public ResponseEntity<String> adminHome() {
        return ResponseEntity.ok("Admin Home Page");
    }

    @GetMapping("/user/home")
    public ResponseEntity<String> userHome() {
        return ResponseEntity.ok("User Home Page");
    }
}
