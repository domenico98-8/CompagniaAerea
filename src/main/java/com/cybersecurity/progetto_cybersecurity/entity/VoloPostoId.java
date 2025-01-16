package com.cybersecurity.progetto_cybersecurity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class VoloPostoId implements Serializable {

    @Column(name = "id_volo")
    private Long idVolo;

    @Column(name = "id_posto")
    private Long idPosto;
}
