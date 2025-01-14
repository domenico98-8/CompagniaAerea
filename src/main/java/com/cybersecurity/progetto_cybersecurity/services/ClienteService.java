package com.cybersecurity.progetto_cybersecurity.services;

import com.cybersecurity.progetto_cybersecurity.controller.dto.ClienteDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.ClienteMapper;
import com.cybersecurity.progetto_cybersecurity.entity.Cliente;
import com.cybersecurity.progetto_cybersecurity.entity.Utente;
import com.cybersecurity.progetto_cybersecurity.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Trova un cliente per ID e restituisci il DTO
    public Optional<ClienteDTO> getClienteById(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.map(ClienteMapper::toDTO);
    }

    // Trova un cliente tramite il documento e restituisci il DTO
    @Transactional
    public ClienteDTO getClienteByDocumento(String documento) {
        Optional<Cliente> cliente = clienteRepository.findByDocumento(documento);
        return cliente.map(ClienteMapper::toDTO).orElse(null);
    }

    // Salva un nuovo cliente (crea entità e restituisce il DTO)
    @Transactional
    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        Cliente cliente = ClienteMapper.toEntity(clienteDTO);
        Cliente savedCliente = clienteRepository.save(cliente);
        return ClienteMapper.toDTO(savedCliente);
    }

    // Aggiorna un cliente esistente e restituisce il DTO
    @Transactional
    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato con id " + id));

        // Imposta i nuovi valori
        cliente.setNome(clienteDTO.getNome());
        cliente.setCognome(clienteDTO.getCognome());
        cliente.setDataNascita(clienteDTO.getDataNascita());
        cliente.setDocumento(clienteDTO.getDocumento());

        // Se l'utente è presente, aggiorna anche l'utente
        if (clienteDTO.getIdUtente() != null) {
            Utente utente = new Utente();
            utente.setId(clienteDTO.getIdUtente());
            cliente.setUtente(utente);
        }

        Cliente updatedCliente = clienteRepository.save(cliente);
        return ClienteMapper.toDTO(updatedCliente);
    }

    // Elimina un cliente per ID
    @Transactional
    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato con id " + id));
        clienteRepository.delete(cliente);
    }
}

