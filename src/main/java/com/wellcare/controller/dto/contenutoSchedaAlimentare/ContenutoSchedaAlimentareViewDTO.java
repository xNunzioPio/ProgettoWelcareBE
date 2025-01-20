package com.wellcare.controller.dto.contenutoSchedaAlimentare;


import lombok.Data;

import java.util.List;

@Data
public class ContenutoSchedaAlimentareViewDTO {

    private Integer id;
    private String priorita;
    private Integer quantita;
    private Integer giorno;
    private String pasto;
    private Integer cibo;
    private Integer schedaAlimentare;
    private Integer principale;//id ciclico che fa riferimento ad un'altra entry di ContenutoSchedaAlimentare
    private String nomeCibo;
    private String nomeGiorno;
    private List<ContenutoSchedaAlimentareViewDTO> alternative;
}
