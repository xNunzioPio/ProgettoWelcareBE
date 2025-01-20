

package com.wellcare.controller.dto.abbonamento;

import lombok.Data;

@Data
public class AbbonamentoViewDTO {

    private Integer id;
    private String nome;
    private Integer prezzo;
    private Integer numMaxCliente;
    private String descrizione;

}
