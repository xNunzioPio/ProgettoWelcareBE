package com.wellcare.service.pastiGiornalieri;


import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroCreationDTO;
import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroUpdateDTO;
import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroViewDTO;
import com.wellcare.exceptions.PastiGiornalieriException;

import java.util.Date;
import java.util.List;

public interface PastiGiornalieriService {

    public PastoGiornalieroViewDTO createPastoGiornaliero(PastoGiornalieroCreationDTO dto);
    public void removePastoGiornaliero(Integer id);
    public PastoGiornalieroViewDTO updatePastoGiornaliero(PastoGiornalieroUpdateDTO dto, Integer idRigaTabella) throws PastiGiornalieriException;

    public List<PastoGiornalieroViewDTO> findAll() throws PastiGiornalieriException;
    public PastoGiornalieroViewDTO findById(Integer id) throws PastiGiornalieriException;
    public List<PastoGiornalieroViewDTO> findByGiorno(Integer idScheda,Integer giorno) throws PastiGiornalieriException;
    public List<PastoGiornalieroViewDTO> findBySchedaAlimentare(Integer idSchedaAlimentare) throws PastiGiornalieriException;
    public List<PastoGiornalieroViewDTO> findByIdCliente (Integer idCliente) throws PastiGiornalieriException;
    public List<PastoGiornalieroViewDTO> findByDataCreazione(Integer id, String data);
}