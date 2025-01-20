package com.wellcare.controller.dto.schedaAllenamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import lombok.Data;

import java.util.Date;


@Data
public class SchedaAllenamentoViewDTO {

    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataCreazione;
    private String note;
    private Integer idCliente;
    private Integer idPersonalTrainer;
    private ClienteViewDTO clienteDTO;
}