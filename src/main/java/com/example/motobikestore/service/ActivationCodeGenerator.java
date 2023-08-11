package com.example.motobikestore.service;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
@Service
public class ActivationCodeGenerator {
    private static final int CODE_LENGTH = 6;

    public static String generateNumberCode() {
        SecureRandom random = new SecureRandom();

        // Generate a random number between 100000 and 999999 (6 digits)
        int randomCode = random.nextInt(900000) + 100000;

        return String.valueOf(randomCode);
    }

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomString() {
        StringBuilder randomString = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < 24; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }
    public static void main(String[] args) {
        String activationCode = generateNumberCode();
        System.out.println("Activation Code: " + activationCode);
    }
}
