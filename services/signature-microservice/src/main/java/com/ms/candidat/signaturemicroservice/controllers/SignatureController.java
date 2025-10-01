package com.ms.candidat.signaturemicroservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms.candidat.signaturemicroservice.models.Signature;
import com.ms.candidat.signaturemicroservice.services.SignatureService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/signatures")
@RequiredArgsConstructor
public class SignatureController {
    private final SignatureService signatureService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Signature> uploadSignature(@RequestParam("file") MultipartFile file ,    HttpServletRequest req) {
        try {
            String cookie = req.getHeader("Cookie");
            return ResponseEntity.ok(signatureService.uploadSignature(file,cookie));
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Signature> getMySignature(HttpServletRequest req) {
        String cookie = req.getHeader("Cookie");
        Signature sig = signatureService.getActiveSignature(cookie);
        if (sig == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(sig);
    }
}
