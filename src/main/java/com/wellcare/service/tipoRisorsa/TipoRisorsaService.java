package com.wellcare.service.tipoRisorsa;


import com.wellcare.controller.dto.tipoRisorsa.TipoRisorsaViewDTO;
import com.wellcare.exceptions.TipoRisorsaException;
import java.util.List;

public interface TipoRisorsaService {
    public List<TipoRisorsaViewDTO> getAll() throws TipoRisorsaException;
    public TipoRisorsaViewDTO getById(Integer id) throws TipoRisorsaException;
}
