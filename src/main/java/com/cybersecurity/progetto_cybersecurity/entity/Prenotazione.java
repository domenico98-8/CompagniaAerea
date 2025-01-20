package com.cybersecurity.progetto_cybersecurity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "prenotazione", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_volo", "id", "id_cliente", "id_posto"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Prenotazione {

    @EmbeddedId
    private PrenotazioneId id;  // La chiave primaria composta

    @Column(name = "id_bagaglio")
    private Long idBagaglio;

    @ManyToOne
    @JoinColumn(name = "id_bagaglio", referencedColumnName = "id", insertable = false, updatable = false)
    private Bagaglio bagaglio;

    @ManyToOne
    @JoinColumn(name = "id_volo", referencedColumnName = "id", insertable = false, updatable = false)
    private Volo volo;

    @ManyToOne
    @JoinColumn(name = "id_posto", referencedColumnName = "id", insertable = false, updatable = false)
    private Posto posto;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", insertable = false, updatable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_utente", referencedColumnName = "id")
    private Utente utente;

    @Column(name = "data_prenotazione")
    private LocalDateTime dataPrenotazione;

    @Column(name = "costo")
    private double costo;

    @Column(name = "checkin")
    private boolean checkin;
}
