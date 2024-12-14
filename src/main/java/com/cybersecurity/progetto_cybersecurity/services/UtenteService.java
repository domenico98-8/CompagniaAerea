package com.cybersecurity.progetto_cybersecurity.services;


import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import com.cybersecurity.progetto_cybersecurity.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Optional<Utente> trovaPerEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    public Utente salvaUtente(Utente utente) {
        return utenteRepository.save(utente);
    }
}