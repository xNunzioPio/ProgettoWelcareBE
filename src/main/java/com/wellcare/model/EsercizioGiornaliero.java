package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "daily_exercise")
@Data
public class EsercizioGiornaliero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date data;
    private Integer ripetizioni;
    @OneToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente Cliente;
    @OneToOne
    @JoinColumn(name = "id_esercizio", referencedColumnName = "id")
    private Esercizio esercizio;
    @OneToOne
    @JoinColumn(name = "id_scheda_allenamento", referencedColumnName = "id")
    private SchedaAllenamento schedaAllenamento;
}
