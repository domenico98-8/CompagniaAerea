package com.cybersecurity.progetto_cybersecurity.repository;

import com.cybersecurity.progetto_cybersecurity.entity.Cliente;
import com.cybersecurity.progetto_cybersecurity.entity.Prenotazione;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> getPrenotazioneByIdUtente(Long idUtente);

    @Query("SELECT c FROM Prenotazione p INNER JOIN Cliente c on p.cliente.id=c.id and p.id=:idPrenotazione")
    List<Cliente> getClientebyIdPrenotazione(@Param("idPrenotazione") Long idPrenotazione);
}
