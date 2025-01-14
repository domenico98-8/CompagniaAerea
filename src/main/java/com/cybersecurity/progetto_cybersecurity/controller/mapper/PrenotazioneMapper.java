package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.PrenotazioneDTO;
import com.cybersecurity.progetto_cybersecurity.entity.*;
import com.cybersecurity.progetto_cybersecurity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PrenotazioneMapper {

    @Autowired
    private PostoRepository postoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VoloRepository voloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private BagaglioRepository bagaglioRepository;

    // Mappa Prenotazione -> PrenotazioneDTO
    public static PrenotazioneDTO prenotazioneToPrenotazioneDTO(Prenotazione prenotazione) {
        if (prenotazione == null) {
            return null;
        }
        return PrenotazioneDTO.builder()
                .id(prenotazione.getId())
                .idVolo(prenotazione.getVolo().getId())
                .idPosto(prenotazione.getPosto().getId())
                .idCliente(prenotazione.getCliente() != null ? prenotazione.getCliente().getId() : null)
                .idBagaglio(prenotazione.getBagalio() != null ? prenotazione.getBagalio().getId() : null)
                .dataPrenotazione(prenotazione.getDataPrenotazione())
                .costo(prenotazione.getCosto())
                .idUtente(prenotazione.getUtente().getId())
                .build();
    }

    // Mappa PrenotazioneDTO -> Prenotazione
    public Prenotazione prenotazioneDTOToPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        if (prenotazioneDTO == null) {
            return null;
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setId(prenotazioneDTO.getId());
        Optional<Posto> posto =postoRepository.findById(prenotazioneDTO.getIdPosto());
        prenotazione.setPosto(posto.orElse(null));
        Optional<Cliente> cliente=clienteRepository.findById(prenotazioneDTO.getIdCliente());
        prenotazione.setCliente(cliente.orElse(null));
        Optional<Volo> volo=voloRepository.findById(prenotazioneDTO.getIdVolo());
        prenotazione.setVolo(volo.orElse(null));
        Optional<Utente> utente=utenteRepository.findById(prenotazioneDTO.getIdUtente());
        prenotazione.setUtente(utente.orElse(null));
        Optional<Bagaglio> bagaglio=bagaglioRepository.findById(prenotazioneDTO.getIdBagaglio());
        prenotazione.setBagalio(bagaglio.orElse(null));
        prenotazione.setCosto(prenotazioneDTO.getCosto());
        prenotazione.setDataPrenotazione(LocalDateTime.now());

        return prenotazione;
    }
}
