package com.example.motobikestore.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value("${api_key}")
    private String apikey;
    @Value("${api_secret}")
    private String apisecret;
    @Value("${cloud_name}")
    private String cloudname;
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary result = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudname,
                "api_key", apikey,
                "api_secret", apisecret,
                "secure", true
        ));
        return result;
    }
}