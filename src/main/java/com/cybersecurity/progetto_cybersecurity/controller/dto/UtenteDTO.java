package com.cybersecurity.progetto_cybersecurity.controller.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UtenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String ruolo;
    private String password;
    private String sesso;
    private String documento;
    private Date dataNascita;
}