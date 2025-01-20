package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "resource_type")
@Data
public class TipoRisorsa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;

}