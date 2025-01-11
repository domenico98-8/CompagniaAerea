package com.cybersecurity.progetto_cybersecurity.utility;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoloPostoResponse {
    private boolean stato;
    private String numeroPosto;
}
