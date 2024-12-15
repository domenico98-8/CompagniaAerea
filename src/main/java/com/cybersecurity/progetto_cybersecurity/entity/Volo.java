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

    @Column(name = "codice_volo", unique = true, nullable = false)
    private String codiceVolo;

    @Column(name = "partenza_da", nullable = false)
    private String partenzaDa;
    @Column(name = "destinazione_a", nullable = false)
    private String destinazioneA;
    @Column(name = "data_partenza", nullable = false)
    private LocalDateTime dataPartenza;
    @Column(name = "data_arrivo", nullable = false)
    private LocalDateTime dataArrivo;
    @Column(name = "prezzo", nullable = false)
    private double prezzo;
}