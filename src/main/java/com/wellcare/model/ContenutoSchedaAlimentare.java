package com.wellcare.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "food_sheet_content")
@Data
public class ContenutoSchedaAlimentare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String priorita;
    private Integer quantita;
    private Integer giorno;
    private String pasto;
    @OneToOne
    @JoinColumn(name = "id_cibo", referencedColumnName = "id")
    private Cibo cibo;
    @OneToOne
    @JoinColumn(name = "id_scheda_alimentare", referencedColumnName = "id")
    private SchedaAlimentare schedaAlimentare;
    private Integer principale;//id ciclico che fa riferimento ad un'altra entry di ContenutoSchedaAllenamento

}