package com.cybersecurity.progetto_cybersecurity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prenotazione")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="id_utente",insertable=false, updatable=false)
    private Long idUtente;

    @ManyToOne
    @JoinColumn(name ="id_bagalio", referencedColumnName = "id")
    private Bagaglio bagalio;

    @ManyToOne
    @JoinColumn(name = "id_volo", referencedColumnName = "id", nullable = false)
    private Volo volo;

    @ManyToOne
    @JoinColumn(name = "id_posto", referencedColumnName = "id", nullable = false)
    private Posto posto;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_utente", referencedColumnName = "id")
    private Utente utente;

    @Column(name = "data_prenotazione")
    private LocalDateTime dataPrenotazione;

    @Column(name = "costo")
    private double costo;

}
