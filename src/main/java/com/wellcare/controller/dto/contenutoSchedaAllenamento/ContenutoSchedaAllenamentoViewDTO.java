
package com.wellcare.controller.dto.contenutoSchedaAllenamento;

import lombok.Data;

import java.util.List;

@Data
public class ContenutoSchedaAllenamentoViewDTO {

    private Integer id;
    private String priorita;
    private Integer ripetizioni;
    private Integer giorno;
    private Integer esercizio;
    private String nomeEsercizio;
    private Integer schedaAllenamento;
    private Integer principale;//id ciclico che fa riferimento ad un'altra entry di ContenutoSchedaAllenamento
    private List<ContenutoSchedaAllenamentoViewDTO> alternative;

}
