package com.wellcare.controller.dto.eserciziGiornalieri;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EsercizioGiornalieroUpdateDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date data;
    private Integer ripetizioni;
    private Integer idEsercizio;

}
