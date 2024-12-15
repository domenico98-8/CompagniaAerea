package com.cybersecurity.progetto_cybersecurity.controller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrenotazioneDTO {
    private Long id;
    private Long utenteId;  // Solo l'ID dell'utente
    private Long voloId;    // Solo l'ID del volo
    private LocalDateTime dataPrenotazione;
}