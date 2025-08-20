package com.ms.candidat.userjwt.controllers;

import com.ms.candidat.userjwt.models.AuthenticationRequest;
import com.ms.candidat.userjwt.models.AuthenticationResponse;
import com.ms.candidat.userjwt.models.RegisterRequest;
import com.ms.candidat.userjwt.models.User;
import com.ms.candidat.userjwt.services.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;



    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){

        return ResponseEntity.ok(authenticationService.register(request));

    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse authResponse = authenticationService.authenticate(request);

        // Set JWT in HttpOnly cookie
        Cookie cookie = new Cookie("jwt", authResponse.getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // only in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 day
        response.addCookie(cookie);

        // Optional: don't send token in body anymore
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(authenticationService.getAllUsers());
    }

    @GetMapping("/getUsers/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(authenticationService.getUserById(id));
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        return ResponseEntity.ok(authenticationService.getProfile());
    }



}
