package com.ms.candidat.signaturemicroservice.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ms.candidat.client.UserClient;
import com.ms.candidat.signaturemicroservice.Dto.UserDTO;
import com.ms.candidat.signaturemicroservice.Repo.SignatureRepo;
import com.ms.candidat.signaturemicroservice.models.Signature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SignatureService {

    private final SignatureRepo signatureRepo;
    private final UserClient userClient;
    private final Cloudinary cloudinary;

    /**
     * Upload a new signature, deactivate old one, save new one, return signature with Cloudinary URL
     */
    public Signature uploadSignature(MultipartFile file, String cookie) throws IOException {
        // Récupérer l’utilisateur connecté
        UserDTO user = userClient.getProfile(cookie);
        Long customerId = user.getCustomer().getId();

        // Désactiver l’ancienne signature si elle existe
        signatureRepo.findByCustomerIdAndActiveTrue(customerId).ifPresent(sig -> {
            sig.setActive(false);
            signatureRepo.save(sig);
        });

        // Uploader sur Cloudinary
        var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", "signatures", // pour organiser les fichiers
                "public_id", "signature_" + customerId, // nom custom
                "overwrite", true // remplacer si ré-upload
        ));

        String url = (String) uploadResult.get("secure_url");

        // Créer et sauvegarder la nouvelle signature
        Signature newSig = Signature.builder()
                .customerId(customerId)
                .signatureUrl(url)
                .active(true)
                .build();

        return signatureRepo.save(newSig);
    }

    /**
     * Retourne la signature active de l’utilisateur
     */
    public Signature getActiveSignature(String cookie) {
        UserDTO user = userClient.getProfile(cookie);
        Long customerId = user.getCustomer().getId();
        return signatureRepo.findByCustomerIdAndActiveTrue(customerId).orElse(null);
    }
}
