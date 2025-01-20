package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "food")
@Data
public class Cibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
}