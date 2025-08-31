package com.stb.credit.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadFile(byte[] fileBytes, String fileName) {
        try {
            Map uploadResult = cloudinary.uploader().upload(fileBytes, ObjectUtils.asMap(
                    "resource_type", "auto",
                    "public_id", fileName
            ));
            return uploadResult.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to Cloudinary", e);
        }
    }
    
    
    public byte[] downloadFile(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        try (InputStream in = connection.getInputStream()) {
            return in.readAllBytes();
        }
    }

    public ByteArrayResource downloadAsResource(String fileUrl) throws IOException {
        byte[] fileData = downloadFile(fileUrl);
        return new ByteArrayResource(fileData);
    }
}
