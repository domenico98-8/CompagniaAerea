package com.cybersecurity.progetto_cybersecurity.controller;


import com.cybersecurity.progetto_cybersecurity.controller.dto.UtenteDTO;
import com.cybersecurity.progetto_cybersecurity.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}