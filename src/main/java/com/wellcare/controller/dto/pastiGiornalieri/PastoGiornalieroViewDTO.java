package com.wellcare.controller.dto.pastiGiornalieri;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PastoGiornalieroViewDTO {

    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date data;
    private Integer quantita;
    private String pasto;
    private Integer idCliente;
    private Integer idCibo;
    private String nomeCibo;
    private Integer idSchedaAlimentare;

}
