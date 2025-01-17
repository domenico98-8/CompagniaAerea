package com.cybersecurity.progetto_cybersecurity.repository;


import com.cybersecurity.progetto_cybersecurity.entity.Cliente;
import com.cybersecurity.progetto_cybersecurity.entity.Volo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoloRepository extends JpaRepository<Volo, Long> {

    Optional<Volo> findById(Long id);

    @Query("SELECT v FROM Volo v WHERE v.partenzaDa LIKE %:from% AND v.destinazioneA LIKE %:to% AND " +
            "v.dataPartenza >= :fromDate AND v.dataPartenza <= :maxDate")
    List<Volo> findVoloByFromToDate(@Param("from") String from,
                                    @Param("to") String to,
                                    @Param("fromDate") LocalDateTime fromDate,
                                    @Param("maxDate") LocalDateTime max
                                    );

    Optional<Volo> findVoloByCodiceVolo(String codiceVolo);
}