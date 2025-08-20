package com.ms.candidat.userjwt.services;

import com.ms.candidat.userjwt.models.AuthenticationRequest;
import com.ms.candidat.userjwt.models.AuthenticationResponse;
import com.ms.candidat.userjwt.models.RegisterRequest;
import com.ms.candidat.userjwt.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import com.ms.candidat.userjwt.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private static final SecureRandom RND = new SecureRandom();

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        Long clientNumber = generateUniqueClientNumber();
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())// Use the role from the request
                .clientNumber(clientNumber)
                .phone(request.getPhone())
                .build();
        UserRepo.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .statusCode(HttpStatus.OK.value())
                .build();
    }
    /**
     * Random 10-digit number (1 000 000 000 – 9 999 999 999).
     * Retries until the DB confirms uniqueness.
     */
    private Long generateUniqueClientNumber() {
        int attempts = 0;
        long candidate;
        do {
            candidate = 1_000_000_000L + RND.nextLong(9_000_000_000L);
            attempts++;
            if (attempts > 10) {                 // ultra-safe fallback
                candidate = System.nanoTime() & 0x3FFFFFFFFFFFFFFFL;
            }
        } while (UserRepo.existsByClientNumber(candidate));

        return candidate;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = UserRepo.findByEmail(request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole())
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    public List<User> getAllUsers() {
        return UserRepo.findAll();
    }

    public User getUserById(Integer id) {
        return UserRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public User getProfile() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return UserRepo.findByEmail(username);
    }


}
