package com.ms.candidat.userjwt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.candidat.userjwt.dtos.UserDTO;
import com.ms.candidat.userjwt.models.AuthenticationRequest;
import com.ms.candidat.userjwt.models.AuthenticationResponse;
import com.ms.candidat.userjwt.models.RegisterRequest;
import com.ms.candidat.userjwt.models.User;
import com.ms.candidat.userjwt.services.AuthenticationService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

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

        Cookie cookie = new Cookie("jwt", authResponse.getToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);   // <-- false for localhost
        cookie.setPath("/");
//        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

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
    public ResponseEntity<UserDTO> getProfile() {
        return ResponseEntity.ok(authenticationService.getProfile());
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);          // delete cookie
        res.addCookie(cookie);
        return ResponseEntity.ok().build();
    }



}
