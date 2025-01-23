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
}
