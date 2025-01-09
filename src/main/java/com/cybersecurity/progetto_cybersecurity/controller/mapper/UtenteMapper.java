package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.UtenteDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import org.springframework.stereotype.Component;

@Component
public class UtenteMapper {

    public UtenteDTO toDTO(Utente utente) {
        if (utente == null) {
            return null;
        }
        
        UtenteDTO dto = new UtenteDTO();
        dto.setId(utente.getId());
        dto.setEmail(utente.getEmail());
        dto.setRuolo(utente.getRuolo());
        dto.setPassword(utente.getPassword());
        return dto;
    }

    public Utente toEntity(UtenteDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Utente utente = new Utente();
        utente.setId(dto.getId());
        utente.setEmail(dto.getEmail());
        utente.setRuolo(dto.getRuolo());
        utente.setPassword(dto.getPassword());
        return utente;
    }
}