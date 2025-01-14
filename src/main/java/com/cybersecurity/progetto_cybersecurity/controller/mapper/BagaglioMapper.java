package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.BagaglioDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Bagaglio;
import org.springframework.stereotype.Component;


@Component
public class BagaglioMapper {

    // Metodo per convertire Bagaglio in BagaglioDTO
    public BagaglioDTO toDto(Bagaglio bagaglio) {
        if (bagaglio == null) {
            return null;
        }

        // Crea e restituisce un BagaglioDTO
        BagaglioDTO bagaglioDTO = new BagaglioDTO();
        bagaglioDTO.setId(bagaglio.getId()); // Imposta l'ID
        bagaglioDTO.setDescrizione(bagaglio.getDescrizione()); // Imposta la descrizione
        bagaglioDTO.setPeso(bagaglio.getPeso());

        return bagaglioDTO;
    }

    // Metodo per convertire BagaglioDTO in Bagaglio
    public Bagaglio toEntity(BagaglioDTO bagaglioDto) {
        if (bagaglioDto == null) {
            return null;
        }

        // Crea e restituisce un Bagaglio
        Bagaglio bagaglio = new Bagaglio();
        bagaglio.setId(bagaglioDto.getId()); // Imposta l'ID
        bagaglio.setDescrizione(bagaglioDto.getDescrizione()); // Imposta la descrizione
        bagaglio.setPeso(bagaglioDto.getPeso());

        return bagaglio;
    }
}

