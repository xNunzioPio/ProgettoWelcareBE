
package com.wellcare.controller.dto.nutrizionista;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NutrizionistaViewDTO {

    private Integer id;
    private String indirizzo;
    private Integer idAbbonamento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataUltimoPagamento;
    private Integer idUtente;
    private List<ClienteViewDTO> listaClienti;
    private List<ClienteViewDTO> listaRichiestaClienti;
}
