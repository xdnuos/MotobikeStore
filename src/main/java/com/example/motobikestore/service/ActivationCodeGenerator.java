package com.example.motobikestore.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
@Service
public class ActivationCodeGenerator {
    private static final int CODE_LENGTH = 6;

    public static String generateActivationCode() {
        SecureRandom random = new SecureRandom();

        // Generate a random number between 100000 and 999999 (6 digits)
        int randomCode = random.nextInt(900000) + 100000;

        return String.valueOf(randomCode);
    }

    public static void main(String[] args) {
        String activationCode = generateActivationCode();
        System.out.println("Activation Code: " + activationCode);
    }
}
