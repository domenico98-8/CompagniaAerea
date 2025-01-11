package com.cybersecurity.progetto_cybersecurity.controller;

import com.cybersecurity.progetto_cybersecurity.controller.dto.ClienteDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.PostoDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.PrenotazioneDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloPostoDTO;
import com.cybersecurity.progetto_cybersecurity.services.*;
import com.cybersecurity.progetto_cybersecurity.utility.PrenotazioneRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    PostoService postoService;

    @Autowired
    VoloPostoService voloPostoService;

    @Autowired
    VoloService voloService;

    @PostMapping("/prenota")
    public ResponseEntity<String> creaPrenotazione(@RequestBody PrenotazioneRequest prenotazione) {
        prenotazione.getPasseggeri().forEach(passeggeri -> {
            if(clienteService.getClienteByDocumento(passeggeri.getDocumento())==null) {
                passeggeri.setId(clienteService.saveCliente(passeggeri).getId());
            }else{
                passeggeri.setId(clienteService.getClienteByDocumento(passeggeri.getDocumento()).getId());
            }
        });
        prenotazione.getPosti().forEach(posto ->{
            VoloPostoDTO voloPosto = new VoloPostoDTO();
            posto.setId(postoService.getPostoByNumeroPosto(posto).getId());
        });

        List<PrenotazioneDTO> listPrenotazioni= new ArrayList<>();
        List<VoloPostoDTO> listVoloPosto= new ArrayList<>();
        Long idVolo=voloService.getIdVoloByCodiceVolo(prenotazione.getVolo());

        for(int i=0; i<prenotazione.getPasseggeri().size(); i++) {
            ClienteDTO passeggero= prenotazione.getPasseggeri().get(i);
            PostoDTO posto=prenotazione.getPosti().get(i);
            PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO(
                    null,
                    idVolo,
                    posto.getId(),
                    passeggero.getId(),
                    LocalDateTime.now(),
                    prenotazione.getCosto()
            );
            listPrenotazioni.add(prenotazioneDTO);
            VoloPostoDTO voloPostoDTO = new VoloPostoDTO(idVolo,posto.getId(),true);
            listVoloPosto.add(voloPostoDTO);
        }

        voloPostoService.saveAll(listVoloPosto);
        prenotazioneService.saveAll(listPrenotazioni);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("OK");
    }

}