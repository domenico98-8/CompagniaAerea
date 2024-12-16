package com.cybersecurity.progetto_cybersecurity.controller.mapper;

import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoloMapper {

    public VoloDTO toDTO(Volo volo) {
        if (volo == null) {
            return null;
        }
        
        VoloDTO dto = new VoloDTO();
        dto.setId(volo.getId());
        dto.setCodiceVolo(volo.getCodiceVolo());
        dto.setPartenzaDa(volo.getPartenzaDa());
        dto.setDestinazioneA(volo.getDestinazioneA());
        populateDateAndTime(dto, volo.getDataPartenza(), true);
        populateDateAndTime(dto, volo.getDataArrivo(), false);
        dto.setPrezzo(volo.getPrezzo());
        return dto;
    }

    public Volo toEntity(VoloDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Volo volo = new Volo();
        volo.setId(dto.getId());
        volo.setCodiceVolo(dto.getCodiceVolo());
        volo.setPartenzaDa(dto.getPartenzaDa());
        volo.setDestinazioneA(dto.getDestinazioneA());
        volo.setDataPartenza(convertToLocalDateTime(dto.getDataPartenza(),dto.getOrarioPartenza()));
        volo.setDataArrivo(convertToLocalDateTime(dto.getDataArrivo(),dto.getOrarioArrivo()));
        volo.setPrezzo(dto.getPrezzo());
        return volo;
    }

    public List<VoloDTO> toDTOList(List<Volo> voli) {
        if (voli == null || voli.isEmpty()) {
            return new ArrayList<>();
        }
        return voli.stream()
                .map(this::toDTO) // Usa il metodo esistente toDTO
                .collect(Collectors.toList());
    }

    public List<Volo> toEntityList(List<VoloDTO> voloDTOs) {
        if (voloDTOs == null || voloDTOs.isEmpty()) {
            return new ArrayList<>();
        }
        return voloDTOs.stream()
                .map(this::toEntity) // Usa il metodo esistente toEntity
                .collect(Collectors.toList());
    }

    private LocalDateTime convertToLocalDateTime(Date data, String orario) {
        if (data == null) {
            return null;
        }

        // Conversione da java.util.Date a LocalDate
        LocalDate localDate = data.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if(orario==null || orario.isBlank()) {
            orario = "00:00";
        }
        String[] parts = orario.split(":");
        return LocalDateTime.of(
                localDate.getYear(),
                localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        );
    }
    private void populateDateAndTime(VoloDTO dto, LocalDateTime dateTime, boolean isPartenza) {
        if (dateTime == null) {
            return;
        }

        // Converti la data
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        // Formatta l'ora come stringa
        String time = String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());

        if (isPartenza) {
            dto.setDataPartenza(date);
            dto.setOrarioPartenza(time);
        } else {
            dto.setDataArrivo(date);
            dto.setOrarioArrivo(time);
        }
    }
}