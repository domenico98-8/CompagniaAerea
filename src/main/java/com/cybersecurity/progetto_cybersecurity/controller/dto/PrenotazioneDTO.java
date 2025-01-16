package com.cybersecurity.progetto_cybersecurity.controller.dto;

import com.cybersecurity.progetto_cybersecurity.entity.PrenotazioneId;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrenotazioneDTO {

    private PrenotazioneId prenotazioneId;
    private Long idUtente;
    private Long idBagaglio;
    private boolean checkin;
    private LocalDateTime dataPrenotazione;
    private double costo;
}
