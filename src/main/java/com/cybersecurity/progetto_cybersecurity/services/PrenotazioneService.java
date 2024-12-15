package com.cybersecurity.progetto_cybersecurity.services;


import com.cybersecurity.progetto_cybersecurity.controller.dto.PrenotazioneDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.PrenotazioneMapper;
import com.cybersecurity.progetto_cybersecurity.entity.Prenotazione;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import com.cybersecurity.progetto_cybersecurity.repository.PrenotazioneRepository;
import com.cybersecurity.progetto_cybersecurity.repository.UtenteRepository;
import com.cybersecurity.progetto_cybersecurity.repository.VoloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private PrenotazioneMapper prenotazioneMapper;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private VoloRepository voloRepository;

    public List<PrenotazioneDTO> getAllPrenotazioni() {
        // Recupera tutte le prenotazioni e le converte in DTO
        return prenotazioneRepository.findAll()
                .stream()
                .map(prenotazioneMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PrenotazioneDTO getPrenotazione(Long id) {
        // Trova una prenotazione e la converte in DTO
        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));
        return prenotazioneMapper.toDTO(prenotazione);
    }


    public PrenotazioneDTO savePrenotazione(PrenotazioneDTO prenotazioneDTO) {
        // Recupera le entità correlate (utente e volo) dal database
        Utente utente = utenteRepository.findById(prenotazioneDTO.getUtenteId())
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
        Volo volo = voloRepository.findById(prenotazioneDTO.getVoloId())
                .orElseThrow(() -> new EntityNotFoundException("Volo non trovato"));

        // Converte il DTO in entità, salva nel database e ritorna il DTO
        Prenotazione prenotazione = prenotazioneMapper.toEntity(prenotazioneDTO, utente, volo);
        prenotazione = prenotazioneRepository.save(prenotazione);
        return prenotazioneMapper.toDTO(prenotazione);
    }
}