package com.cybersecurity.progetto_cybersecurity.controller;


import com.cybersecurity.progetto_cybersecurity.controller.dto.UtenteDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import com.cybersecurity.progetto_cybersecurity.services.UtenteService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/registrazione")
    public UtenteDTO registraUtente(@RequestBody UtenteDTO utente) {
        return utenteService.saveUtente(utente);
    }

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
            return ResponseEntity.status(500).body("Richiesta errata!");
        }
        Optional<UtenteDTO> userDto=utenteService.getUtente(email);
        if(userDto.isPresent()){
            UtenteDTO utenteDTO=userDto.get();
            if(password.equals(utenteDTO.getPassword())){
                return ResponseEntity.ok("Login effettuato con successo!");
            }else{
                return ResponseEntity.status(401).body("Credenziali non valide!");
            }
        }else{
            return ResponseEntity.status(404).body("Utente non trovato!");
        }
    }
}