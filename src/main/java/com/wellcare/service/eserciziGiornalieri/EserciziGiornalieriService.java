package com.wellcare.service.eserciziGiornalieri;

import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroCreationDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroUpdateDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroViewDTO;
import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroViewDTO;
import com.wellcare.exceptions.EserciziGiornalieriException;
import com.wellcare.model.EsercizioGiornaliero;


import java.util.List;

public interface EserciziGiornalieriService {

    public EsercizioGiornalieroViewDTO createEsercizioGiornaliero(EsercizioGiornalieroCreationDTO dto) throws EserciziGiornalieriException;
    public void removeEsercizioGiornaliero(Integer id);
    public EsercizioGiornalieroViewDTO updateEsercizioGiornaliero(EsercizioGiornalieroUpdateDTO dto, Integer idRigaTabella) throws EserciziGiornalieriException;

    public List<EsercizioGiornalieroViewDTO> findAll() throws EserciziGiornalieriException;
    public EsercizioGiornalieroViewDTO findById(Integer id) throws EserciziGiornalieriException;
    public List<EsercizioGiornalieroViewDTO> findByGiorno(Integer idScheda,Integer giorno) throws EserciziGiornalieriException;
    public List<EsercizioGiornalieroViewDTO> findBySchedaAllenamento(Integer idSchedaAllenamento) throws EserciziGiornalieriException;
    public List<EsercizioGiornalieroViewDTO> findByIdCliente(Integer idcliente) throws EserciziGiornalieriException;
    public EsercizioGiornalieroViewDTO entityToDTO(EsercizioGiornaliero entity);
    public List<EsercizioGiornalieroViewDTO> findByDataCreazione(Integer id, String data);
}
