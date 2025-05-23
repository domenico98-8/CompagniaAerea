package com.cybersecurity.progetto_cybersecurity.repository;


import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
}