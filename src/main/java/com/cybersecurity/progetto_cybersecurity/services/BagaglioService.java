package com.cybersecurity.progetto_cybersecurity.services;

import com.cybersecurity.progetto_cybersecurity.controller.dto.BagaglioDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.BagaglioMapper;
import com.cybersecurity.progetto_cybersecurity.entity.Bagaglio;
import com.cybersecurity.progetto_cybersecurity.repository.BagaglioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BagaglioService {

    private final BagaglioMapper bagaglioMapper;
    private final BagaglioRepository bagaglioRepository;

    @Autowired
    public BagaglioService(BagaglioMapper bagaglioMapper, BagaglioRepository bagaglioRepository) {
        this.bagaglioMapper = bagaglioMapper;
        this.bagaglioRepository = bagaglioRepository;
    }

    public List<BagaglioDTO> findAll() {
        return bagaglioRepository.findAll().stream()
                .map(bagaglioMapper::toDto)
                .toList();
    }

    public BagaglioDTO save(BagaglioDTO dto) {
        Bagaglio bagaglio = bagaglioMapper.toEntity(dto);
        bagaglio = bagaglioRepository.save(bagaglio);
        return bagaglioMapper.toDto(bagaglio);
    }
}

