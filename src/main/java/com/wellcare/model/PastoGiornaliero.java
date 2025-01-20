package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "daily_meal")
@Data
public class PastoGiornaliero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date data;
    private Integer quantita;
    private String pasto;
    @OneToOne
    @JoinColumn(name = "id_cibo", referencedColumnName = "id")
    private Cibo cibo;
    @OneToOne
    @JoinColumn(name = "id_scheda_alimentare", referencedColumnName = "id")
    private SchedaAlimentare schedaAlimentare;
    @OneToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente Cliente;
}
