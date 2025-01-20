package com.wellcare.controller.dto.schedaAlimentare;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareViewDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SchedaAlimentareViewDTO {

    private Integer id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataCreazione;
    private String note;
    private Integer idCliente;
    private Integer idNutrizionista;
    private ClienteViewDTO clienteDTO;
}
