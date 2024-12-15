package com.cybersecurity.progetto_cybersecurity.controller.dto;

import lombok.Data;

@Data
public class UtenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String ruolo; // Non includiamo la password per motivi di sicurezza
}