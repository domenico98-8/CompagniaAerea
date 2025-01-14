package com.cybersecurity.progetto_cybersecurity.services;


import com.cybersecurity.progetto_cybersecurity.controller.dto.UtenteDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.UtenteMapper;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import com.cybersecurity.progetto_cybersecurity.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteMapper utenteMapper;


    @Transactional
    public UtenteDTO saveUtente(UtenteDTO utenteDTO) {
        // Converte il DTO in entità e lo salva nel database
        Utente utente = utenteMapper.toEntity(utenteDTO);
        utente = utenteRepository.save(utente);
        return utenteMapper.toDTO(utente);
    }

    @Transactional
    public Optional<UtenteDTO> getUtente(String email) {
        Optional<Utente> utente=utenteRepository.findByEmail(email);
        return utente.map(value -> utenteMapper.toDTO(value));
    }

    @Transactional
    public Boolean existUtente(String email) {
        Optional<Utente> utente=utenteRepository.findByEmail(email);
        return utente.isPresent();
    }

    @Transactional
    public void deleteUtente(String email) {
        Optional<Utente> utente=utenteRepository.findByEmail(email);
        utente.ifPresent(value -> utenteRepository.delete(value));
    }
}