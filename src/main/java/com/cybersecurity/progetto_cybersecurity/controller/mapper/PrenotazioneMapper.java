package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.PrenotazioneDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Prenotazione;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import org.springframework.stereotype.Component;

@Component
public class PrenotazioneMapper {

    public PrenotazioneDTO toDTO(Prenotazione prenotazione) {
        if (prenotazione == null) {
            return null;
        }
        
        PrenotazioneDTO dto = new PrenotazioneDTO();
        dto.setId(prenotazione.getId());
        dto.setUtenteId(prenotazione.getUtente() != null ? prenotazione.getUtente().getId() : null);
        dto.setVoloId(prenotazione.getVolo() != null ? prenotazione.getVolo().getId() : null);
        dto.setDataPrenotazione(prenotazione.getDataPrenotazione());
        return dto;
    }

    public Prenotazione toEntity(PrenotazioneDTO dto, Utente utente, Volo volo) {
        if (dto == null) {
            return null;
        }
        
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setId(dto.getId());
        prenotazione.setDataPrenotazione(dto.getDataPrenotazione());
        prenotazione.setUtente(utente);
        prenotazione.setVolo(volo);
        return prenotazione;
    }
}