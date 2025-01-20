package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customer")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "id_utente",referencedColumnName = "id")
    private Utente utente;
    private Integer peso;
    private Float altezza;
    @OneToOne
    @JoinColumn(name = "prest_pt",referencedColumnName = "id")
    private PersonalTrainer prestPersonalTrainer;
    @OneToOne
    @JoinColumn(name = "prest_nt",referencedColumnName = "id")
    private Nutrizionista prestNutrizionsita;

}