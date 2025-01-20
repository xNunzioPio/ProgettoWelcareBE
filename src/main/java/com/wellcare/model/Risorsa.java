package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "resource")
@Data
public class Risorsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String descrizione;
    private String path;
    @OneToOne
    @JoinColumn(name = "id_utente", referencedColumnName = "id")
    private Utente utente;
    @OneToOne
    @JoinColumn(name = "id_tipo", referencedColumnName = "id")
    private TipoRisorsa tipoRisorsa;
}
