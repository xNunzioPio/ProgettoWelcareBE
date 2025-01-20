package com.wellcare.controller.dto.personalTrainer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PersonalTrainerViewDTO {

    private Integer id;
    private Integer idUtente;
    private String indirizzo;
    private Integer idAbbonamento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataUltimoPagamento;
    private List<ClienteViewDTO> listaClienti;
    private List<ClienteViewDTO> listaRichiestaClienti;
}
