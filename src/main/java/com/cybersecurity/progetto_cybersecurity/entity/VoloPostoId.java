package com.cybersecurity.progetto_cybersecurity.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class VoloPostoId implements Serializable {

    @Column(name = "id_volo")
    private Long idVolo;

    @Column(name = "id_posto")
    private Long idPosto;
}
