package com.cybersecurity.progetto_cybersecurity.services;


import com.cybersecurity.progetto_cybersecurity.controller.dto.UtenteDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.UtenteMapper;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import com.cybersecurity.progetto_cybersecurity.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteMapper utenteMapper;

    public List<UtenteDTO> getAllUtenti() {
        // Recupera tutti gli utenti e li converte in DTO
        return utenteRepository.findAll()
                .stream()
                .map(utenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UtenteDTO saveUtente(UtenteDTO utenteDTO) {
        // Converte il DTO in entità e lo salva nel database
        Utente utente = utenteMapper.toEntity(utenteDTO);
        utente = utenteRepository.save(utente);
        return utenteMapper.toDTO(utente);
    }

    public Optional<UtenteDTO> getUtente(String email) {
        Optional<Utente> utente=utenteRepository.findByEmail(email);
        return utente.map(value -> utenteMapper.toDTO(value));
    }

    public Boolean existUtente(String email) {
        Optional<Utente> utente=utenteRepository.findByEmail(email);
        return utente.isPresent();
    }

    public void deleteUtente(String email) {
        Optional<Utente> utente=utenteRepository.findByEmail(email);
        utente.ifPresent(value -> utenteRepository.delete(value));
    }
}