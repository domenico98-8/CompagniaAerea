package com.cybersecurity.progetto_cybersecurity.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoloPostoDTO {
    private Long idVolo;
    private Long idPosto;
    private boolean stato;
}

