package com.wellcare.service.schedaAlimentare;

import com.wellcare.controller.dto.cibo.CiboCreationDTO;
import com.wellcare.controller.dto.cibo.CiboViewDTO;
import java.util.List;

public interface CiboService {
    public CiboViewDTO createCibo(CiboCreationDTO scheda);
    public void removeCibo (Integer id);
    public List<CiboViewDTO> getAll();
    public CiboViewDTO getById(Integer id);
    public List<CiboViewDTO> findByNome(String nome);
}
