package com.ms.candidat.userjwt.controllers;

import com.ms.candidat.userjwt.models.BanquierRequest;
import com.ms.candidat.userjwt.models.User;
import com.ms.candidat.userjwt.services.BanquierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/banquier")
public class BanquierController {

    @Autowired
    private BanquierService banquierService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createBanquier(@Valid @RequestBody BanquierRequest dto) {
        User saved = banquierService.createBanquier(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllBanquiers() {
        return ResponseEntity.ok(banquierService.getAllBanquiers());
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateBanquier(
            @PathVariable Integer id,
            @Valid @RequestBody BanquierRequest dto) {
        return ResponseEntity.ok(banquierService.updateBanquier(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBanquier(@PathVariable Integer id) {
        banquierService.deleteBanquier(id);
        return ResponseEntity.noContent().build();
    }



}
