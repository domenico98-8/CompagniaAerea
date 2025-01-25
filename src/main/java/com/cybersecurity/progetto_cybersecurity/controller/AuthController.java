package com.cybersecurity.progetto_cybersecurity.controller;

import com.cybersecurity.progetto_cybersecurity.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cybersecurity.progetto_cybersecurity.utility.Utils.createResponse;
import static com.cybersecurity.progetto_cybersecurity.utility.Utils.extractUserCodeFromCookie;

@CrossOrigin(origins = "https://localhost", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/check")
    public ResponseEntity<?> checkAuthentication(HttpServletRequest request) {
        // Recupera il cookie con il JWT
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String jwt = cookie.getValue();
                    // Verifica il token
                    if (jwtUtil.validateToken(jwt)) {
                        return ResponseEntity.ok().body(createResponse("Authenticated",HttpStatus.ACCEPTED.value()));
                    }
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createResponse("Not authenticated",HttpStatus.UNAUTHORIZED.value()));
    }


    @GetMapping("/getCodiceUtente")
    public ResponseEntity<String> getCodiceUtente(HttpServletRequest response) {
        String codiceUtente=extractUserCodeFromCookie(response);

        return  ResponseEntity.ok(codiceUtente);
    }
}

