package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "training_sheet_content")
@Data
public class ContenutoSchedaAllenamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    private String priorita;
    private Integer ripetizioni;
    private Integer giorno;
    @OneToOne
    @JoinColumn(name = "id_esercizio", referencedColumnName = "id")
    private Esercizio esercizio;
    @OneToOne
    @JoinColumn(name = "id_scheda_allenamento", referencedColumnName = "id")
    private SchedaAllenamento schedaAllenamento;
    private Integer principale;//id ciclico che fa riferimento ad un'altra entry di ContenutoSchedaAllenamento

}