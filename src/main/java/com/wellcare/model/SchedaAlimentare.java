package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "food_sheet")
@Data
public class SchedaAlimentare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_creazione", nullable = false)
    private Date dataCreazione;
    private String note;
    @OneToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "id_nutrizionista", referencedColumnName = "id")
    private Nutrizionista nutrizionista;

}