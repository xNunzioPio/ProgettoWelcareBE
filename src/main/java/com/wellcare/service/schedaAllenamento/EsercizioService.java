package com.wellcare.service.schedaAllenamento;

import com.wellcare.controller.dto.esercizio.EsercizioCreationDTO;
import com.wellcare.controller.dto.esercizio.EsercizioViewDTO;
import com.wellcare.exceptions.EsercizioException;

import java.util.List;

public interface EsercizioService {


    public EsercizioViewDTO createEsercizio(EsercizioCreationDTO e);
    public void removeEsercizio(Integer id);

    public List<EsercizioViewDTO> findAll() throws EsercizioException;
    public EsercizioViewDTO findById(Integer id) throws EsercizioException;
    public List<EsercizioViewDTO> findByNome(String nome) throws EsercizioException;
    public String findDescrizione(Integer id);

}
