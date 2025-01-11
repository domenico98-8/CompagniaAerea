package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloPostoDTO;
import com.cybersecurity.progetto_cybersecurity.entity.VoloPosto;
import com.cybersecurity.progetto_cybersecurity.entity.VoloPostoId;
import com.cybersecurity.progetto_cybersecurity.services.PostoService;
import com.cybersecurity.progetto_cybersecurity.services.VoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoloPostoMapper {

    @Autowired
    private VoloService voloService;

    @Autowired
    VoloMapper voloMapper;

    @Autowired
    private PostoService postoService;


    public VoloPostoDTO toDto(VoloPosto entity) {
        VoloPostoDTO dto = new VoloPostoDTO();
        dto.setIdVolo(entity.getVolo().getId());
        dto.setIdPosto(entity.getPosto().getId());
        dto.setStato(entity.getStato());
        return dto;
    }

    public VoloPosto toEntity(VoloPostoDTO dto) {
        VoloPosto entity = new VoloPosto();
        VoloPostoId idVoloPosto = new VoloPostoId();
        idVoloPosto.setIdPosto(dto.getIdPosto());
        idVoloPosto.setIdVolo(dto.getIdVolo());
        entity.setId(idVoloPosto);
        entity.setStato(dto.isStato());
        entity.setVolo(voloMapper.toEntity(voloService.getVolo(dto.getIdVolo())));
        entity.setPosto(PostoMapper.postoDTOToPosto(postoService.getPostoById(dto.getIdPosto())));
        return entity;
    }
}

