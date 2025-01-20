package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subscription")
@Data
public class Abbonamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Integer prezzo;
    @Column(name = "num_max_cliente", nullable = false)
    private Integer numMaxCliente;
    private String descrizione;


}