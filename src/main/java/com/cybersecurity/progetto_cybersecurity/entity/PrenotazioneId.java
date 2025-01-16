package com.cybersecurity.progetto_cybersecurity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PrenotazioneId implements Serializable {
    @Column(name = "id")
    private Long id;
    @Column(name = "id_volo")
    private Long id_volo;
    @Column(name = "id_posto")
    private Long id_posto;
    @Column(name = "id_cliente")
    private Long id_cliente;
}
