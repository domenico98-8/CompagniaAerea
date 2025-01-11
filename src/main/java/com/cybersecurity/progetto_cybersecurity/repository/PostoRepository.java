package com.cybersecurity.progetto_cybersecurity.repository;

import com.cybersecurity.progetto_cybersecurity.controller.dto.VoloPostoDTO;
import com.cybersecurity.progetto_cybersecurity.entity.Cliente;
import com.cybersecurity.progetto_cybersecurity.entity.Posto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostoRepository extends JpaRepository<Posto, Long> {

    @Query("SELECT new com.cybersecurity.progetto_cybersecurity.controller.dto.VoloPostoDTO(vp.stato,vp.posto.numeroPosto) FROM Posto p INNER JOIN VoloPosto vp ON p.id=vp.posto.id INNER JOIN Volo v ON vp.volo.id=v.id WHERE v.codiceVolo=:codiceVolo ORDER BY vp.volo.id")
    public List<VoloPostoDTO> findByVolo(String codiceVolo);

    Optional<Posto> findByNumeroPosto(String numeroPosto);
}

