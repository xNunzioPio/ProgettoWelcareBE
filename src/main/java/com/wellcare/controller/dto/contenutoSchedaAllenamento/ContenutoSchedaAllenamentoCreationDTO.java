package com.wellcare.controller.dto.contenutoSchedaAllenamento;

import lombok.Data;

import java.util.List;

@Data
public class ContenutoSchedaAllenamentoCreationDTO {

    private String priorita;
    private Integer ripetizioni;
    private Integer giorno;
    private Integer esercizio;
    private Integer principale;
    private List<ContenutoSchedaAllenamentoCreationDTO> alternative;

}
