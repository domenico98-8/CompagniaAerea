package com.cybersecurity.progetto_cybersecurity.repository;

import com.cybersecurity.progetto_cybersecurity.entity.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> getPrenotazioneByIdUtente(Long idUtente);
    // Puoi aggiungere query personalizzate qui se necessario
}
