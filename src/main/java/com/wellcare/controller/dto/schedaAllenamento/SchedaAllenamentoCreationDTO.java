package com.wellcare.controller.dto.schedaAllenamento;

import com.wellcare.controller.dto.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoCreationDTO;
import lombok.Data;


import java.util.List;

@Data
public class SchedaAllenamentoCreationDTO {

    private String note;
    private Integer idCliente;
    private Integer idPersonalTrainer;
    private List<ContenutoSchedaAllenamentoCreationDTO> contenutoScheda;

}
