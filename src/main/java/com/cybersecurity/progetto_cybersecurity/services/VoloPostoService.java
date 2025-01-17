package com.cybersecurity.progetto_cybersecurity.services;

import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloPostoDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.VoloPostoMapper;
import com.cybersecurity.progetto_cybersecurity.entity.VoloPosto;
import com.cybersecurity.progetto_cybersecurity.entity.VoloPostoId;
import com.cybersecurity.progetto_cybersecurity.repository.VoloPostoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoloPostoService {

    @Autowired
    private VoloPostoRepository voloPostoRepository;

    @Autowired
    private VoloPostoMapper voloPostoMapper;

    @Transactional
    public VoloPostoDTO getById(VoloPostoId id) {
        Optional<VoloPosto> entity = voloPostoRepository.findById(id);
        return entity.map(voloPostoMapper::toDto).orElse(null);
    }

    @Transactional
    public VoloPostoDTO save(VoloPostoDTO dto) {
        VoloPosto entity = voloPostoMapper.toEntity(dto);
        // Associazioni con `Volo` e `Posto` devono essere gestite qui
        VoloPosto savedEntity = voloPostoRepository.save(entity);
        return voloPostoMapper.toDto(savedEntity);
    }

}

