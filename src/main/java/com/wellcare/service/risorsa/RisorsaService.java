package com.wellcare.service.risorsa;


import com.wellcare.controller.dto.risorsa.RisorsaCreationDTO;
import com.wellcare.controller.dto.risorsa.RisorsaViewDTO;
import com.wellcare.exceptions.RisorsaException;
import java.util.List;

public interface RisorsaService{

    public RisorsaViewDTO createRisorsa(RisorsaCreationDTO dto);
    public void removeRisorsa(Integer id);
    public List<RisorsaViewDTO> findByIdUtente(Integer idUtente);
    public List<RisorsaViewDTO> findByIdTipo(Integer idTipo);
    public List<RisorsaViewDTO> findByPath(String path);

    public List<RisorsaViewDTO> findAll() throws RisorsaException;
    public RisorsaViewDTO findById(Integer id) throws RisorsaException;
}
