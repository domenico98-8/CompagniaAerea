package com.cybersecurity.progetto_cybersecurity.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BigliettoResponse {
    private String nome;
    private String cognome;
    private String documento;
    private String bagaglio;
    private String posto;
}
