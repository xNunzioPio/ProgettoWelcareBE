package com.wellcare.controller.dto.registration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class NutrizionistaCreationDTO {

    private String indirizzo;
    private Integer idAbbonamento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataUltimoPagamento;
    private Integer idUtente;
}
