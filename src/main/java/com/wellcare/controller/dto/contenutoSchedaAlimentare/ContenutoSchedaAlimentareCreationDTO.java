package com.wellcare.controller.dto.contenutoSchedaAlimentare;

import lombok.Data;

import java.util.List;

@Data
public class ContenutoSchedaAlimentareCreationDTO {

    private String priorita;
    private Integer quantita;
    private Integer giorno;
    private String pasto;
    private Integer cibo;
    private Integer principale;
    private List<ContenutoSchedaAlimentareCreationDTO> alternative;
}
