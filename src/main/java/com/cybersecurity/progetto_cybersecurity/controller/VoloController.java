package com.cybersecurity.progetto_cybersecurity.controller;


import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import com.cybersecurity.progetto_cybersecurity.services.VoloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "https://localhost", allowCredentials = "true")
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
    public ResponseEntity<String> inserisciVoli(@RequestBody Volo volo) {
        try {
            voloService.salvaVolo(volo);
            return ResponseEntity.status(HttpStatus.CREATED).body("Success"); // Status 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"); // Status 400 Bad Request
        }
    }

    @PostMapping("/cercaVoli")
    public ResponseEntity<List<VoloDTO>> cercaVoli(@RequestBody VoloDTO voloDto) {
        try {
            List<VoloDTO> voli = voloService.trovaVoliAndata(voloDto);
            setDurataVoli(voli);
            log.info("Numero voli trovati:{}",voli.size());
            return ResponseEntity.ok(voli); // Status 200 OK
        } catch (Exception e) {
            log.error("Errore nella ricerca dei voli",e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Status 404 Not Found
        }
    }

    private void setDurataVoli(List<VoloDTO> voli) {
        for (VoloDTO volo : voli) {
            if (volo.getOrarioPartenza() != null && volo.getOrarioArrivo() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                LocalTime partenza = LocalTime.parse(volo.getOrarioPartenza(), formatter);
                LocalTime arrivo = LocalTime.parse(volo.getOrarioArrivo(), formatter);

                Duration durata = Duration.between(partenza, arrivo);

                volo.setDurataVolo(durata.toHours()+"H"+ (durata.toMinutes() % 60) + "min");
            } else {
                volo.setDurataVolo("Dati insufficienti"); // Gestione dei casi senza date
            }
        }
    }
}