package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "nutritionist")
@Data
public class Nutrizionista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "id_utente", referencedColumnName = "id")
    private Utente utente;
    private String indirizzo;
    @OneToOne
    @JoinColumn(name = "id_abbonamento", referencedColumnName = "id")
    private Abbonamento abbonamento;
    @Column(name = "data_ultimo_pagamento", nullable = false)
    private Date dataUltimoPagamento;
    @Column(name = "prest_cl")
    private String listaIdClienti;
    @Column(name = "rich_prest_cl")
    private String listaRichiesteIdClienti;

}