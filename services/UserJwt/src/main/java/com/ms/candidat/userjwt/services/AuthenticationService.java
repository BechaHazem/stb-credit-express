package com.ms.candidat.userjwt.services;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.candidat.userjwt.client.CreditClient;
import com.ms.candidat.userjwt.dtos.CustomerDTO;
import com.ms.candidat.userjwt.dtos.UserDTO;
import com.ms.candidat.userjwt.models.AuthenticationRequest;
import com.ms.candidat.userjwt.models.AuthenticationResponse;
import com.ms.candidat.userjwt.models.RegisterRequest;
import com.ms.candidat.userjwt.models.Role;
import com.ms.candidat.userjwt.models.User;
import com.ms.candidat.userjwt.repositories.UserRepository;

import jakarta.transaction.Transactional;

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
    
    @Autowired
    private CreditClient creditClient;

    @Autowired
    private ModelMapper modelMapper;

    private static final SecureRandom RND = new SecureRandom();

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        Long clientNumber = generateUniqueClientNumber();
        String username;
        if (request.getRole() == Role.CLIENT) {
            username = clientNumber.toString();
        } else {
            username = request.getEmail();
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .clientNumber(clientNumber)
                .phone(request.getPhone())
                .username(username)
                .build();
        UserRepo.save(user);

        return AuthenticationResponse.builder()
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

        	candidate = 1_000_000_000L + (RND.nextLong() & Long.MAX_VALUE) % 9_000_000_000L;
            attempts++;
            if (attempts > 10) {            
                candidate = System.nanoTime() & 0x3FFFFFFFFFFFFFFFL;
            }
        } while (UserRepo.existsByClientNumber(candidate));

        return candidate;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = UserRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
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
    public UserDTO getProfile() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = UserRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDTO dto = modelMapper.map(user, UserDTO.class);

        if (user.getRole() == Role.CLIENT) {
            CustomerDTO customer = creditClient.getCustomer(user.getCustomerId());
            dto.setCustomer(customer);
        }

        return dto;
    }
    
    public List<UserDTO> findByAgence(String agence) {
        return UserRepo.findByAgenceAndRole(agence, Role.BANQUIER)
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());    }


}
