package com.cybersecurity.progetto_cybersecurity.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneResponse {
    private String codicePrenotazione;
    private String origine;
    private String destinazione;
    private String orario;
    private String numero_passeggeri;
}
