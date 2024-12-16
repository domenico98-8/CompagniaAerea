package com.cybersecurity.progetto_cybersecurity.services;


import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloDTO;
import com.cybersecurity.progetto_cybersecurity.controller.mapper.VoloMapper;
import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import com.cybersecurity.progetto_cybersecurity.repository.VoloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class VoloService {

    @Autowired
    private VoloRepository voloRepository;

    @Autowired
    private VoloMapper voloMapper;


    public VoloDTO getVolo(Long id) {
        Volo volo = voloRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Volo non trovato"));
        return voloMapper.toDTO(volo);
    }

    public List<Volo> trovaTutti() {
        return voloRepository.findAll();
    }


    public List<VoloDTO> trovaVoliAndata(VoloDTO voloDto) {
        Volo volo=voloMapper.toEntity(voloDto);
        LocalDateTime maxTime =volo.getDataPartenza().toLocalDate().atTime(LocalTime.MAX);
        List<VoloDTO> voliDTO=voloMapper.toDTOList(voloRepository.findVoloByFromToDate(volo.getPartenzaDa(),volo.getDestinazioneA(),volo.getDataPartenza(), maxTime));
        return voliDTO;
    }

    public Volo salvaVolo(Volo volo) {
        return voloRepository.save(volo);
    }
}