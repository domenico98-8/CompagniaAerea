package com.cybersecurity.progetto_cybersecurity.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "volo")
public class Volo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codiceVolo;

    private String partenzaDa;
    private String destinazioneA;

    private LocalDateTime dataPartenza;
    private LocalDateTime dataArrivo;

    private double prezzo;
}