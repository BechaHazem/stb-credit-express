package com.ms.candidat.userjwt.services;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.candidat.userjwt.models.BanquierRequest;
import com.ms.candidat.userjwt.models.Role;
import com.ms.candidat.userjwt.models.User;
import com.ms.candidat.userjwt.repositories.UserRepository;


@Service
public class BanquierService {

    @Autowired
    private UserRepository UserRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final SecureRandom RND = new SecureRandom();

    public User createBanquier(BanquierRequest dto) {
        Long clientNumber = generateUniqueClientNumber();
        String username;
        username = dto.email();


        return UserRepo.save(
                User.builder()
                        .firstname(dto.firstname())
                        .lastname(dto.lastname())
                        .email(dto.email())
                        .password(passwordEncoder.encode(dto.phone()))
                        .phone(dto.phone())
                        .agence(dto.agence())
                        .role(Role.BANQUIER)
                        .clientNumber(clientNumber)
                        .username(username)
                        .build()
        );
    }


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

    public User updateBanquier(Integer id, BanquierRequest dto) {
        String username;
        username = dto.email();

        User user = UserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Banquier not found"));

        if (!user.getRole().equals(Role.BANQUIER)) {
            throw new RuntimeException("User is not a banker");
        }

        user.setFirstname(dto.firstname());
        user.setLastname(dto.lastname());
        user.setEmail(dto.email());
        user.setPhone(dto.phone());
        user.setAgence(dto.agence());
        user.setPassword(passwordEncoder.encode(dto.phone()));
        user.setUsername(username);
        return UserRepo.save(user);
    }
    //delete banquier
    public void deleteBanquier(Integer id) {
        User user = UserRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Banquier not found"));

        if (!user.getRole().equals(Role.BANQUIER)) {
            throw new RuntimeException("User is not a banker");
        }
        UserRepo.delete(user);
    }

    public List<User> getAllBanquiers() {
        return UserRepo.findAll()
                .stream()
                .filter(u -> u.getRole() == Role.BANQUIER)
                .toList();
    }





}
