package com.wellcare.service.schedaAllenamento;

import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.controller.dto.schedaAllenamento.SchedaAllenamentoCreationDTO;
import com.wellcare.controller.dto.schedaAllenamento.SchedaAllenamentoViewDTO;
import com.wellcare.exceptions.SchedaAllenamentoException;


import java.util.Date;
import java.util.List;

public interface SchedaAllenamentoService {


    public SchedaAllenamentoViewDTO createSchedaAllenamento(SchedaAllenamentoCreationDTO scheda);
    //public SchedaAllenamentoViewDTO createSchedaAllenamento(SchedaAllenamentoCreationDTO scheda, List<ContenutoSchedaAllenamentoCreationDTO> dtos) throws ContenutoSchedaAllenamentoException;
    public SuccessResponseDTO removeSchedaAllenamento(Integer id) throws SchedaAllenamentoException;
    public List<SchedaAllenamentoViewDTO> getAll() throws SchedaAllenamentoException;
    public SchedaAllenamentoViewDTO getById(Integer id) throws SchedaAllenamentoException;
    public List<SchedaAllenamentoViewDTO> findByIdPersonalTrainer(Integer idPersonalTrainer) throws SchedaAllenamentoException;
    public List<SchedaAllenamentoViewDTO> findByIdCliente(Integer idCliente) throws SchedaAllenamentoException;
    public List<SchedaAllenamentoViewDTO> findByDataCreazione(Date date) throws SchedaAllenamentoException;
    public String findNote(Integer id);





}
