package com.cybersecurity.progetto_cybersecurity.controller;


import com.cybersecurity.progetto_cybersecurity.controller.dto.UtenteDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import com.cybersecurity.progetto_cybersecurity.jwt.JwtUtil;
import com.cybersecurity.progetto_cybersecurity.services.UtenteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final JwtUtil jwtUtil;

    public UtenteController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/registrazione")
    public ResponseEntity<String> registraUtente(@RequestBody UtenteDTO utente) {
        if(utenteService.existUtente(utente.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Utente già registrato con questa email.");
        }
        // Hash della password usando BCrypt
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(utente.getPassword());

        // Imposta la password hashata nell'oggetto utente
        utente.setPassword(hashedPassword);
        utenteService.saveUtente(utente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Utente Creato");
    }

    //Restiruire un token
    @PostMapping("/login")
    public ResponseEntity<String> loginUtente(@RequestBody String credenziali) {
        ObjectMapper objectMapper = new ObjectMapper();
        String email = "";
        String password = "";
        try {
            // Converti la stringa JSON in un oggetto JsonNode
            JsonNode jsonNode = objectMapper.readTree(credenziali);

            // Estrai i campi dal JSON
            email = jsonNode.get("email").asText();
            password = jsonNode.get("password").asText();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Richiesta errata!");
        }
        Optional<UtenteDTO> userDto=utenteService.getUtente(email);
        if(userDto.isPresent()){
            UtenteDTO utenteDTO=userDto.get();
            if(password.equals(utenteDTO.getPassword())){
                // Genera un token
                String token = jwtUtil.generateToken(password);
                return ResponseEntity.ok(token);
            }else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali non valide");
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utente non trovato!");
        }
    }

}