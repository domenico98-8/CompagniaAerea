package com.cybersecurity.progetto_cybersecurity.security;

import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PasswordService {

    private final PasswordConfig passwordConfig;

    public PasswordService(PasswordConfig passwordConfig) {
        this.passwordConfig = passwordConfig;
    }

    public String hashPassword(String password) {
        String salt = passwordConfig.getSalt();
        String saltedPassword = salt + password;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(saltedPassword.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing the password", e);
        }
    }
}

