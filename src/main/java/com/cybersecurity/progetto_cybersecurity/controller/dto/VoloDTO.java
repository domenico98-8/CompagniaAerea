package com.cybersecurity.progetto_cybersecurity.controller.dto;

import lombok.Data;

import java.util.Date;

@Data
public class VoloDTO {
    private Long id;
    private String codiceVolo;
    private String partenzaDa;
    private String destinazioneA;
    private Date dataPartenza;
    private String orarioPartenza;
    private Date dataArrivo;
    private String orarioArrivo;
    private double prezzo;
}