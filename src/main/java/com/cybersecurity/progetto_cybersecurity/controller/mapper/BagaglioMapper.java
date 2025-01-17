package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.BagaglioDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Bagaglio;
import org.springframework.stereotype.Component;


@Component
public class BagaglioMapper {

    public BagaglioDTO toDto(Bagaglio bagaglio) {
        if (bagaglio == null) {
            return null;
        }

        BagaglioDTO bagaglioDTO = new BagaglioDTO();
        bagaglioDTO.setId(bagaglio.getId()); // Imposta l'ID
        bagaglioDTO.setDescrizione(bagaglio.getDescrizione()); // Imposta la descrizione
        bagaglioDTO.setPeso(bagaglio.getPeso());

        return bagaglioDTO;
    }

    public Bagaglio toEntity(BagaglioDTO bagaglioDto) {
        if (bagaglioDto == null) {
            return null;
        }

        Bagaglio bagaglio = new Bagaglio();
        bagaglio.setId(bagaglioDto.getId()); // Imposta l'ID
        bagaglio.setDescrizione(bagaglioDto.getDescrizione()); // Imposta la descrizione
        bagaglio.setPeso(bagaglioDto.getPeso());

        return bagaglio;
    }
}

