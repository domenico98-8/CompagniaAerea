package com.cybersecurity.progetto_cybersecurity.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Entity
@Table(name = "volo_posto")
@Getter
@Setter
@EqualsAndHashCode(of = {"volo", "posto"}) // Equals and hashCode usando volo e posto come chiavi
@ToString(exclude = {"volo", "posto"}) // Esclude le entità volo e posto per evitare loop infiniti in toString
public class VoloPosto {

    @EmbeddedId
    private VoloPostoId id;

    @ManyToOne
    @MapsId("idVolo")  // Mappa la colonna id_volo alla chiave primaria composta
    @JoinColumn(name = "id_volo", referencedColumnName = "id")
    private Volo volo;

    @ManyToOne
    @MapsId("idPosto") // Mappa la colonna id_posto alla chiave primaria composta
    @JoinColumn(name = "id_posto", referencedColumnName = "id")
    private Posto posto;

    @Column(name = "stato")
    private Boolean stato;
}

