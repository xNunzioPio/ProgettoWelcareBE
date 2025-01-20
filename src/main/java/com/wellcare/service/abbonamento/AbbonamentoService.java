package com.wellcare.service.abbonamento;

import com.wellcare.controller.dto.abbonamento.AbbonamentoViewDTO;
import com.wellcare.exceptions.AbbonamentoException;

import java.util.List;

public interface AbbonamentoService {


    public List<AbbonamentoViewDTO> getAll() throws AbbonamentoException;
    public AbbonamentoViewDTO getById(Integer id) throws AbbonamentoException;


}
