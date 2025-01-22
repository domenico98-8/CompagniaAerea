package com.cybersecurity.progetto_cybersecurity.security;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class InputValidator {

    // Regex per validare un indirizzo email standard
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public boolean isValidString(String input) {
        // Esegui controlli di validità su stringhe (evita caratteri pericolosi)
        return input != null && input.matches("^[a-zA-Z0-9_ ]*$");
    }

    public boolean isValidId(Long id) {
        // Controlla che l'ID non sia negativo o nullo
        return id != null && id > 0;
    }

    /**
     * Metodo per validare un'email.
     * @param email L'email da validare.
     * @return true se l'email è valida, false altrimenti.
     */
    public boolean isValid(String email) {
        if (email == null || email.isEmpty()) {
            return false; // Email non valida se è null o vuota
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}

