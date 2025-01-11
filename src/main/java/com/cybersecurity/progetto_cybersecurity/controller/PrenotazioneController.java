package com.cybersecurity.progetto_cybersecurity.controller;

import com.cybersecurity.progetto_cybersecurity.services.PrenotazioneService;
import com.cybersecurity.progetto_cybersecurity.utility.PrenotazioneRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping("/prenota")
    public ResponseEntity<String> creaPrenotazione(@RequestBody PrenotazioneRequest prenotazione) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("OK");
    }



}