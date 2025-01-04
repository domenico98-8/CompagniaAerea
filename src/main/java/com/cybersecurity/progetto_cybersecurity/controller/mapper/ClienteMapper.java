package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.ClienteDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Cliente;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCognome(cliente.getCognome());
        dto.setDataNascita(cliente.getDataNascita());
        dto.setDocumento(cliente.getDocumento());

        if (cliente.getUtente() != null) {
            dto.setIdUtente(cliente.getUtente().getId());
        }

        return dto;
    }

    public static Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setCognome(dto.getCognome());
        cliente.setDataNascita(dto.getDataNascita());
        cliente.setDocumento(dto.getDocumento());

        // Imposta l'utente, se necessario, a partire dall'ID (opzionale)
        if (dto.getIdUtente() != null) {
            Utente utente = new Utente();
            utente.setId(dto.getIdUtente());
            cliente.setUtente(utente);
        }

        return cliente;
    }
}

