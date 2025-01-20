package com.wellcare.service.schedaAlimentare;

import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.schedaAlimentare.SchedaAlimentareCreationDTO;
import com.wellcare.controller.dto.schedaAlimentare.SchedaAlimentareViewDTO;
import com.wellcare.exceptions.ContenutoSchedaAlimentareException;
import com.wellcare.exceptions.SchedaAlimentareException;
import com.wellcare.exceptions.SchedaAllenamentoException;
import com.wellcare.model.Cliente;
import com.wellcare.model.SchedaAlimentare;

import java.util.Date;
import java.util.List;

public interface SchedaAlimentareService {
    public SchedaAlimentareViewDTO createSchedaAlimentare(SchedaAlimentareCreationDTO scheda) throws SchedaAlimentareException;
    public void removeSchedaAlimentare(Integer id);
    public List<SchedaAlimentareViewDTO> getAll();
    public SchedaAlimentareViewDTO getById(Integer id);

    public List<SchedaAlimentareViewDTO> findByIdNutrizionista(Integer idNutrizionista);
    public List<SchedaAlimentareViewDTO> findByIdCliente(Integer idCliente);
    public List<SchedaAlimentareViewDTO> findByDataCreazione(Date date);

    public SchedaAlimentare schedaAlimentareCreateCostructor(SchedaAlimentareCreationDTO dto) throws SchedaAlimentareException;
    public SchedaAlimentareViewDTO entityToDTO(SchedaAlimentare entity);
    public ClienteViewDTO clienteEntityToDTO(Cliente cliente);
}
