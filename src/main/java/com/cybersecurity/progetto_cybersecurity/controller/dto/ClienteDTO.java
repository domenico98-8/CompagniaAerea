package com.cybersecurity.progetto_cybersecurity.controller.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ClienteDTO {

    private Long id;
    private String nome;
    private String cognome;
    private Date dataNascita;
    private String documento;
    private Long idUtente;

}

