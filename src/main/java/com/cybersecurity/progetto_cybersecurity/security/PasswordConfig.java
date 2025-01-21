package com.cybersecurity.progetto_cybersecurity.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfig {

    @Value("${security.password.salt}")
    private String salt;

    public String getSalt() {
        return salt;
    }
}

