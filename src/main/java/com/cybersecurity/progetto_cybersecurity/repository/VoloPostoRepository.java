package com.cybersecurity.progetto_cybersecurity.repository;


import com.cybersecurity.progetto_cybersecurity.entity.VoloPosto;
import com.cybersecurity.progetto_cybersecurity.entity.VoloPostoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoloPostoRepository extends JpaRepository<VoloPosto, VoloPostoId> {
    // Aggiungi metodi personalizzati, se necessario
}

