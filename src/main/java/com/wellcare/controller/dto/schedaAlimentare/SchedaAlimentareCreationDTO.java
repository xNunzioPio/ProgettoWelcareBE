package com.wellcare.controller.dto.schedaAlimentare;

import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareCreationDTO;
import lombok.Data;

import java.util.List;

@Data
public class SchedaAlimentareCreationDTO {
    private String note;
    private Integer idCliente;
    private Integer idNutrizionista;
    private List<ContenutoSchedaAlimentareCreationDTO> contenutoScheda;
}
