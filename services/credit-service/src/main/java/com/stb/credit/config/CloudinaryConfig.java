package com.stb.credit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dl0lojylt",
                "api_key", "767981914582673",
                "api_secret", "ShTfF12zYbhVJmc65pTNuYSJ7EU",
                "secure", true
        ));
    }
}
