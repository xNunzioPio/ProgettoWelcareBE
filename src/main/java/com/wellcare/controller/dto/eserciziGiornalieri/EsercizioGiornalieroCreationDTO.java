package com.wellcare.controller.dto.eserciziGiornalieri;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EsercizioGiornalieroCreationDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date data;
    private Integer ripetizioni;
    private Integer idCliente;
    private Integer idEsercizio;
    private Integer idSchedaAllenamento;

}
