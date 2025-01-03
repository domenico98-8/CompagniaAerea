package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.PrenotazioneDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Posto;
import com.cybersecurity.progetto_cybersecurity.entity.Prenotazione;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import com.cybersecurity.progetto_cybersecurity.repository.PostoRepository;
import com.cybersecurity.progetto_cybersecurity.repository.UtenteRepository;
import com.cybersecurity.progetto_cybersecurity.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PrenotazioneMapper {

    @Autowired
    private static PostoRepository postoRepository;

    @Autowired
    private static UtenteRepository utenteRepository;

    @Autowired
    private static VoloRepository voloRepository;

    // Mappa Prenotazione -> PrenotazioneDTO
    public static PrenotazioneDTO prenotazioneToPrenotazioneDTO(Prenotazione prenotazione) {
        if (prenotazione == null) {
            return null;
        }
        return PrenotazioneDTO.builder()
                .id(prenotazione.getId())
                .idVolo(prenotazione.getVolo().getId())
                .idPosto(prenotazione.getPosto().getId())
                .idUtente(prenotazione.getUtente() != null ? prenotazione.getUtente().getId() : null)
                .dataPrenotazione(prenotazione.getDataPrenotazione())
                .costo(prenotazione.getCosto())
                .build();
    }

    // Mappa PrenotazioneDTO -> Prenotazione
    public static Prenotazione prenotazioneDTOToPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        if (prenotazioneDTO == null) {
            return null;
        }
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setId(prenotazioneDTO.getId());
        Optional<Posto> posto =postoRepository.findById(prenotazioneDTO.getIdPosto());
        prenotazione.setPosto(posto.orElse(null));
        Optional<Utente> utente=utenteRepository.findById(prenotazioneDTO.getIdUtente());
        prenotazione.setUtente(utente.orElse(null));
        Optional<Volo> volo=voloRepository.findById(prenotazioneDTO.getIdVolo());
        prenotazione.setVolo(volo.orElse(null));
        prenotazione.setCosto(prenotazioneDTO.getCosto());

        return prenotazione;
    }
}
