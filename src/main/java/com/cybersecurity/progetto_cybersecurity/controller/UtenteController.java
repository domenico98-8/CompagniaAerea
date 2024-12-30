package com.cybersecurity.progetto_cybersecurity.controller;


import com.cybersecurity.progetto_cybersecurity.controller.dto.UtenteDTO;
import com.cybersecurity.progetto_cybersecurity.jwt.JwtUtil;
import com.cybersecurity.progetto_cybersecurity.services.UtenteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        // Imposta la password hashata nell'oggetto utente
        utente.setPassword(generateSHA256Hash(utente.getPassword()));
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

    private static String generateSHA256Hash(String input) {
        try {
            // Ottieni l'istanza del digest SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Calcola l'hash della stringa input
            byte[] hash = digest.digest(input.getBytes());

            // Converte l'array di byte in una stringa esadecimale
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                // Converti ogni byte in un valore esadecimale e aggiungilo alla stringa
                hexString.append(String.format("%02x", b));
            }

            // Restituisce l'hash in formato esadecimale
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.", e);
        }
    }

}