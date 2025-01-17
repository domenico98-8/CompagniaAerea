package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.PostoDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Posto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostoMapper {

    public static PostoDTO postoToPostoDTO(Posto posto) {
        if (posto == null) {
            return null;
        }
        PostoDTO postoDTO = new PostoDTO();
        postoDTO.setId(posto.getId());
        postoDTO.setNumeroPosto(posto.getNumeroPosto());
        return postoDTO;
    }

    public static Posto postoDTOToPosto(PostoDTO postoDTO) {
        if (postoDTO == null) {
            return null;
        }
        Posto posto = new Posto();
        posto.setId(postoDTO.getId());
        posto.setNumeroPosto(postoDTO.getNumeroPosto());
        return posto;
    }

    public static List<PostoDTO> postoListToPostoDTOList(List<Posto> posti) {
        if (posti == null) {
            return null;
        }
        return posti.stream()
                .map(PostoMapper::postoToPostoDTO)
                .collect(Collectors.toList());
    }

    public static List<Posto> postoDTOListToPostoList(List<PostoDTO> postoDTOs) {
        if (postoDTOs == null) {
            return null;
        }
        return postoDTOs.stream()
                .map(PostoMapper::postoDTOToPosto)
                .collect(Collectors.toList());
    }
}

