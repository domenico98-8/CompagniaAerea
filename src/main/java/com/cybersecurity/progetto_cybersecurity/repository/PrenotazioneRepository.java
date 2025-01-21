package com.cybersecurity.progetto_cybersecurity.repository;

import com.cybersecurity.progetto_cybersecurity.entity.Cliente;
import com.cybersecurity.progetto_cybersecurity.entity.Prenotazione;
import com.cybersecurity.progetto_cybersecurity.entity.PrenotazioneId;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, PrenotazioneId> {

    @Query("Select p FROM Prenotazione p where p.utente.id=:idUtente")
    List<Prenotazione> getPrenotazioneByIdUtente(@Param("idUtente") Long idUtente);

    @Query("SELECT p FROM Prenotazione p where p.id.id=:idPrenotazione")
    List<Prenotazione> getPrenotazioniById(@Param("idPrenotazione") Long idPrenotazione);

    @Query("SELECT c FROM Prenotazione p INNER JOIN Cliente c on p.cliente.id=c.id and p.id.id=:idPrenotazione")
    List<Cliente> getClientebyIdPrenotazione(@Param("idPrenotazione") Long idPrenotazione);

    @Query("SELECT MAX(p.id.id) FROM Prenotazione p")
    Long getMaxId();

    @Query("SELECT p FROM Prenotazione p where p.cliente.id=:idCliente AND p.volo.id=:codiceVolo")
    Optional<Prenotazione> getPrenotazioneByVoloCliente(@Param("idCliente") Long idCliente,
                                                        @Param("codiceVolo") Long codiceVolo);
}
