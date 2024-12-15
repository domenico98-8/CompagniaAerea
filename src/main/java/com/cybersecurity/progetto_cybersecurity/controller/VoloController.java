package com.cybersecurity.progetto_cybersecurity.controller;


import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import com.cybersecurity.progetto_cybersecurity.services.VoloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/voli")
public class VoloController {

    @Autowired
    private VoloService voloService;

    @GetMapping
    public ResponseEntity<List<Volo>>  trovaTutti() {

        List<Volo> voli = voloService.trovaTutti();
        return ResponseEntity.ok(voli);
    }

    @PostMapping("/inserisciVoli")
    public ResponseEntity<Volo> inserisciVoli(@RequestBody Volo volo) {
        try {
            Volo nuovoVolo = voloService.salvaVolo(volo);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuovoVolo); // Status 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Status 400 Bad Request
        }
    }

    @PostMapping("/cercaVoli")
    public ResponseEntity<List<Volo>> cercaVoli(@RequestBody VoloDTO voloDto) {
        try {
            List<Volo> voli = voloService.trovaVoliAndata(voloDto);
            log.error("Numero voli trovati:{}",voli.size());
            return ResponseEntity.ok(voli); // Status 200 OK
        } catch (Exception e) {
            log.error("Errore nella ricerca dei voli",e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Status 404 Not Found
        }
    }
}