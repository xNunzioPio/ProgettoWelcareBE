package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "training_sheet")
@Data
public class SchedaAllenamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data_creazione", nullable = false)
    private Date dataCreazione;
    @Column(name = "note", nullable = true)
    private String note;

    @OneToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente Cliente;

    @OneToOne
    @JoinColumn(name = "id_personal_trainer", referencedColumnName = "id")
    private PersonalTrainer PersonalTrainer;

}