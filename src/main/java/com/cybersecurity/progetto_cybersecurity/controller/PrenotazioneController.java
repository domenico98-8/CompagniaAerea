package com.cybersecurity.progetto_cybersecurity.controller;

import com.cybersecurity.progetto_cybersecurity.controller.dto.ClienteDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.PostoDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.PrenotazioneDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloPostoDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.PrenotazioneMapper;
import com.cybersecurity.progetto_cybersecurity.entity.*;
import com.cybersecurity.progetto_cybersecurity.security.InputValidator;
import com.cybersecurity.progetto_cybersecurity.services.*;
import com.cybersecurity.progetto_cybersecurity.utility.BigliettoResponse;
import com.cybersecurity.progetto_cybersecurity.utility.CheckinRequest;
import com.cybersecurity.progetto_cybersecurity.utility.PrenotazioneRequest;
import com.cybersecurity.progetto_cybersecurity.utility.PrenotazioneResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@CrossOrigin(origins = "https://localhost", allowCredentials = "true")
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

    @Autowired
    PrenotazioneMapper prenotazioneMapper;

    @Autowired
    InputValidator validator;

    @GetMapping("/le-mie-prenotazioni/{idUtente}")
    public ResponseEntity<List<PrenotazioneResponse>> getPrenotazioni(@PathVariable Long idUtente) {

        if(!validator.isValidId(idUtente)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<Prenotazione> prenotazioni = prenotazioneService.getPrenotazioniFromIdUtente(idUtente);

        List<PrenotazioneResponse> responses = prenotazioni.stream()
                .filter(distinctByKey(prenotazione -> prenotazione.getId().getId())) // Evita duplicati basati sul codice (id prenotazione)
                .map(prenotazione -> {
                    // Raggruppa le prenotazioni per id volo
                    boolean checkinPerVolo = prenotazioni.stream()
                            .filter(p -> p.getId().getId().equals(prenotazione.getId().getId())) // Raggruppo per prenotazione
                            .allMatch(Prenotazione::isCheckin); // Verifica se tutte le prenotazioni hanno checkin=true

                    return new PrenotazioneResponse(
                            prenotazione.getId().getId().toString(),
                            prenotazione.getVolo().getPartenzaDa(),
                            prenotazione.getVolo().getDestinazioneA(),
                            prenotazione.getVolo().getDataPartenza().toLocalTime().toString(),
                            Long.toString(
                                    prenotazioni.stream()
                                            .filter(predicato -> predicato.getVolo().getId().equals(prenotazione.getVolo().getId()))
                                            .count()
                            ),
                            checkinPerVolo // Usa il risultato dell'AND delle prenotazioni per il flag checkin
                    );
                })
                .toList();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(responses);
    }

    @PostMapping("/prenota")
    @Transactional
    public ResponseEntity<String> creaPrenotazione(@RequestBody PrenotazioneRequest prenotazione) {
        prenotazione.getPasseggeri().forEach(passeggeri -> {
            if(clienteService.getClienteByDocumento(passeggeri.getDocumento())==null) {
                passeggeri.setId(clienteService.saveCliente(passeggeri).getId());
            }else{
                passeggeri.setId(clienteService.getClienteByDocumento(passeggeri.getDocumento()).getId());
            }
        });
        prenotazione.getPosti().forEach(posto ->{
            posto.setId(postoService.getPostoByNumeroPosto(posto).getId());
        });

        Long idVolo=voloService.getIdVoloByCodiceVolo(prenotazione.getVolo());

        String registrato=existBook(prenotazione.getPasseggeri(), idVolo);
        if(!registrato.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("L'utente "+registrato+" risulta già prenotato per questo volo!");
        }

        Long maxId=prenotazioneService.getMaxIdPrenotazione();

        Long id=(maxId==null?0:maxId)+1;
        for(int i=0; i<prenotazione.getPasseggeri().size(); i++) {
            ClienteDTO passeggero= prenotazione.getPasseggeri().get(i);
            PostoDTO posto=prenotazione.getPosti().get(i);
            PrenotazioneId prenotazioneId=new PrenotazioneId();
            prenotazioneId.setId(id);
            prenotazioneId.setId_volo(idVolo);
            prenotazioneId.setId_posto(posto.getId());
            prenotazioneId.setId_cliente(passeggero.getId());
            PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO(
                    prenotazioneId,
                    prenotazione.getIdUtente(),
                    null,
                    false,
                    LocalDateTime.now(),
                    prenotazione.getCosto()
            );
            prenotazioneService.savePrenotazione(prenotazioneDTO);
            VoloPostoDTO voloPostoDTO = new VoloPostoDTO(idVolo,posto.getId(),true);
            voloPostoService.save(voloPostoDTO);
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Volo Prenotato!");
    }

    private String existBook(List<ClienteDTO> clienti,Long idVolo){
        for(ClienteDTO cliente:clienti) {
            if(prenotazioneService.getPrenotazioneByVoloCliente(cliente.getId(),idVolo)){
                return cliente.getNome();
            }
        }
        return "";
    }

    @GetMapping("/getClientiFromPrenotazione/{id}")
    public ResponseEntity<List<ClienteDTO>> getClientiFromPrenotazioe(@PathVariable Long id){
        if(!validator.isValidId(id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(prenotazioneService.getClienteByIdPrenotazione(id));
    }

    @PostMapping("/checkin/{idPrenotazione}")
    @Transactional
    public ResponseEntity<String> checkin(@RequestBody List<CheckinRequest> checkin, @PathVariable Long idPrenotazione) {
        try {
            if(!validator.isValidId(idPrenotazione)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            List<PrenotazioneDTO> prenotazioniDTO = prenotazioneService.getPrenotazioneById(idPrenotazione);
            if (prenotazioniDTO == null || prenotazioniDTO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prenotazione non trovata!");
            }

            Map<Long, PrenotazioneDTO> prenotazioniMap = prenotazioniDTO.stream()
                    .collect(Collectors.toMap(c->c.getPrenotazioneId().getId_cliente(), p -> p));

            for (CheckinRequest checkinRequest : checkin) {
                Long idCliente = checkinRequest.getCliente().getId();
                PrenotazioneDTO prenotazioneDTO = prenotazioniMap.get(idCliente);

                if (prenotazioneDTO != null) {
                    prenotazioneDTO.setIdBagaglio(checkinRequest.getBagaglio().getId());
                    prenotazioneDTO.setCheckin(true);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Utente della prenotazione non trovato per il cliente con ID: " + idCliente);
                }
            }

            prenotazioneService.saveAll(new ArrayList<>(prenotazioniMap.values()));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Errore durante il checkin!");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Check-in Effettuato!");
    }

    @DeleteMapping("/cancellaPrenotazione/{idPrenotazione}")
    @Transactional
    public ResponseEntity<Boolean> cancellaPrenotazione(@PathVariable Long idPrenotazione){

        if(!validator.isValidId(idPrenotazione)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        prenotazioneService.deletePrenotazione(idPrenotazione);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
    }

    @GetMapping("/getBiglietti/{idPrenotazione}")
    @Transactional
    public ResponseEntity<List<BigliettoResponse>> getBiglietti(@PathVariable Long idPrenotazione){
        if(!validator.isValidId(idPrenotazione)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<PrenotazioneDTO> prenotazioni=prenotazioneService.getPrenotazioneById(idPrenotazione);
        List<BigliettoResponse> biglietti=new ArrayList<>();
        for(PrenotazioneDTO prenotazione: prenotazioni){
            Prenotazione prenotazioneEntity=prenotazioneMapper.prenotazioneDTOToPrenotazione(prenotazione);
            BigliettoResponse bigliettoResponse = getBigliettoResponse(prenotazioneEntity);
            biglietti.add(bigliettoResponse);
        }
        return ResponseEntity.ok(biglietti);
    }

    private BigliettoResponse getBigliettoResponse(Prenotazione prenotazioneEntity) {
        Cliente clienteEntity= prenotazioneEntity.getCliente();
        Bagaglio bagaglio= prenotazioneEntity.getBagaglio();
        Posto postoEntity= prenotazioneEntity.getPosto();
        String bagaglioString=bagaglio==null?"Bagaglio Non Selezionato":bagaglio.getDescrizione()+" ("+bagaglio.getPeso()+"KG) ";
        return new BigliettoResponse(clienteEntity.getNome(),
                clienteEntity.getCognome(),clienteEntity.getDocumento(),bagaglioString,
                postoEntity.getNumeroPosto());
    }

    public <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }
}