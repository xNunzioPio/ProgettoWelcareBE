package com.wellcare.service.schedaAlimentare;

import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareCreationDTO;
import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareViewDTO;
import com.wellcare.exceptions.ContenutoSchedaAlimentareException;
import com.wellcare.model.ContenutoSchedaAlimentare;
import java.util.List;

public interface ContenutoSchedaAlimentareService {

    public ContenutoSchedaAlimentareViewDTO createContenutoSchedaAlimentare(ContenutoSchedaAlimentareCreationDTO contenuto,Integer idScheda);
    public void removeContenutoSchedaAlimentare(Integer id);
    public List<ContenutoSchedaAlimentareViewDTO> getAll() throws ContenutoSchedaAlimentareException;
    public ContenutoSchedaAlimentareViewDTO getById(Integer id) throws ContenutoSchedaAlimentareException;

    public List<ContenutoSchedaAlimentareViewDTO> findByPriorita(Integer priorita) throws ContenutoSchedaAlimentareException;
    public List<ContenutoSchedaAlimentareViewDTO> findByGiorno(Integer id,String giorno) throws ContenutoSchedaAlimentareException;
    public List<ContenutoSchedaAlimentareViewDTO> findBySchedaAlimentare(Integer schedaAlimentare) throws ContenutoSchedaAlimentareException;
    public List<ContenutoSchedaAlimentareViewDTO> findAlternativeBySchedaPrincipale(Integer principale) throws ContenutoSchedaAlimentareException;
}
