package com.cybersecurity.progetto_cybersecurity.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "utente")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String ruolo; // Es. ADMIN o USER
}