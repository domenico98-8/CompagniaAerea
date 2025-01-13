package com.cybersecurity.progetto_cybersecurity.controller.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrenotazioneDTO {

    private Long id;
    private Long idVolo;
    private Long idPosto;
    private Long idCliente;
    private Long idUtente;
    private LocalDateTime dataPrenotazione;
    private double costo;
}
