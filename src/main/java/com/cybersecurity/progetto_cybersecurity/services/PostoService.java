package com.cybersecurity.progetto_cybersecurity.services;

import com.cybersecurity.progetto_cybersecurity.controller.dto.PostoDTO;
import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloPostoDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.PostoMapper;
import com.cybersecurity.progetto_cybersecurity.entity.Posto;
import com.cybersecurity.progetto_cybersecurity.entity.VoloPosto;
import com.cybersecurity.progetto_cybersecurity.repository.PostoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostoService {

    @Autowired
    private PostoRepository postoRepository;

    // Ottieni tutti i posti e restituisci i DTO
    public List<PostoDTO> getAllPosti() {
        return postoRepository.findAll().stream()
                .map(PostoMapper::postoToPostoDTO)  // Mapper manuale
                .collect(Collectors.toList());
    }

    // Ottieni un posto per ID e restituisci il DTO
    public PostoDTO getPostoById(Long id) {
        Posto posto = postoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Posto non trovato"));
        return PostoMapper.postoToPostoDTO(posto);  // Mapper manuale
    }

    // Crea un nuovo posto e restituisci il DTO
    public PostoDTO createPosto(PostoDTO postoDTO) {
        Posto posto = PostoMapper.postoDTOToPosto(postoDTO);  // Mapper manuale
        Posto createdPosto = postoRepository.save(posto);
        return PostoMapper.postoToPostoDTO(createdPosto);  // Mapper manuale
    }

    // Aggiorna un posto esistente e restituisci il DTO
    public PostoDTO updatePosto(Long id, PostoDTO postoDTO) {
        if (!postoRepository.existsById(id)) {
            throw new RuntimeException("Posto non esistente");
        }
        Posto posto = PostoMapper.postoDTOToPosto(postoDTO);  // Mapper manuale
        posto.setId(id);  // Imposta l'ID per l'aggiornamento
        Posto updatedPosto = postoRepository.save(posto);
        return PostoMapper.postoToPostoDTO(updatedPosto);  // Mapper manuale
    }

    // Elimina un posto
    public void deletePosto(Long id) {
        if (!postoRepository.existsById(id)) {
            throw new RuntimeException("Posto non trovato");
        }
        postoRepository.deleteById(id);
    }

    public List<VoloPostoDTO> getPostiByCodiceVolo(String codiceVolo) {
        return postoRepository.findByVolo(codiceVolo);
    }
}
