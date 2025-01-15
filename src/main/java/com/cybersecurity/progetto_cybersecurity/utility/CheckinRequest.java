package com.cybersecurity.progetto_cybersecurity.utility;

import com.cybersecurity.progetto_cybersecurity.controller.dto.BagaglioDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.ClienteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckinRequest {
    private BagaglioDTO bagaglio;
    private ClienteDTO cliente;
}
