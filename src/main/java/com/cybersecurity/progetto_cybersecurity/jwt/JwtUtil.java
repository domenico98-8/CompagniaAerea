package com.cybersecurity.progetto_cybersecurity.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    //esternalizzare
    @Value("${spring.jwt.secretkey}")
    private String SECRET_KEY; // Usa una chiave sicura!

    // Genera un token JWT
    public String generateToken(String password) {
        return Jwts.builder()
                .setSubject(password)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 ora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Estrai l'username dal token
    public String extractPassword(String token) {
        return getClaims(token).getSubject();
    }

    // Verifica il token
    public boolean validateToken(String token) {
        return getClaims(token).getExpiration().after(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // Metodo per calcolare il tempo di scadenza rimanente
    public long getJwtExpiration(String jwt) {
        try {
            // Ottieni i Claims dal token
            Claims claims = getClaims(jwt);

            // Ottieni il valore di "exp" (expiration time) in secondi
            Long expirationTime = claims.getExpiration().getTime() / 1000;

            // Ottieni l'ora attuale in secondi
            long currentTime = System.currentTimeMillis() / 1000;

            // Calcola i secondi rimanenti
            return expirationTime - currentTime;
        } catch (Exception e) {
            System.err.println("Errore durante il parsing del JWT: " + e.getMessage());
            return 0; // Ritorna 0 in caso di errore o token non valido
        }
    }
}
