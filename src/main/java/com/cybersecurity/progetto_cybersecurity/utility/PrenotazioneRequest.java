package com.cybersecurity.progetto_cybersecurity.utility;

import com.cybersecurity.progetto_cybersecurity.controller.dto.ClienteDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.PostoDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.UtenteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrenotazioneRequest {
    List<ClienteDTO> passeggeri;
    List<PostoDTO> posti;
    Double costo;
    String volo;
}
