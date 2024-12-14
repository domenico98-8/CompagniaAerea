package com.cybersecurity.progetto_cybersecurity.controller;

import com.cybersecurity.progetto_cybersecurity.entity.Prenotazione;
import com.cybersecurity.progetto_cybersecurity.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping
    public Prenotazione creaPrenotazione(@RequestBody Prenotazione prenotazione) {
        return prenotazioneService.salvaPrenotazione(prenotazione);
    }

    @GetMapping("/{utenteId}")
    public List<Prenotazione> prenotazioniPerUtente(@PathVariable Long utenteId) {
        return prenotazioneService.trovaPerUtenteId(utenteId);
    }
}