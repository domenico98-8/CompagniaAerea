package com.cybersecurity.progetto_cybersecurity.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bagaglio")
@Data
public class Bagaglio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descrizione;

    @Column(name = "peso")
    private int peso;
}
