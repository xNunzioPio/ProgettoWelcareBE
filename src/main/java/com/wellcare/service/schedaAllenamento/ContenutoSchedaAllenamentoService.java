package com.wellcare.service.schedaAllenamento;

import com.wellcare.controller.dto.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoCreationDTO;
import com.wellcare.controller.dto.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoViewDTO;
import com.wellcare.exceptions.ContenutoSchedaAllenamentoException;

import java.util.List;

public interface ContenutoSchedaAllenamentoService {

    public ContenutoSchedaAllenamentoViewDTO createContenutoSchedaAllenamento(ContenutoSchedaAllenamentoCreationDTO c,Integer idScheda);
    public void removeContenutoSchedaAllenamento(Integer id);

    public List<ContenutoSchedaAllenamentoViewDTO> findAll() throws ContenutoSchedaAllenamentoException;
    public ContenutoSchedaAllenamentoViewDTO findById(Integer id) throws ContenutoSchedaAllenamentoException;
    public List<ContenutoSchedaAllenamentoViewDTO> findByPriorità(Integer prio) throws ContenutoSchedaAllenamentoException;
    public List<ContenutoSchedaAllenamentoViewDTO> findByGiorno(Integer idScheda,Integer giorno) throws ContenutoSchedaAllenamentoException;
    public List<ContenutoSchedaAllenamentoViewDTO> findBySchedaAllenamento(Integer idSchedaAllenamento) throws ContenutoSchedaAllenamentoException;
    public List<ContenutoSchedaAllenamentoViewDTO> findAlternativeBySchedaPrincipale(Integer idContenutoPrincipale) throws ContenutoSchedaAllenamentoException;

}
