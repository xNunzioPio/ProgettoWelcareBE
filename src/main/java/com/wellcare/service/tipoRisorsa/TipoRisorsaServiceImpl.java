package com.wellcare.service.tipoRisorsa;


import com.wellcare.controller.dto.tipoRisorsa.TipoRisorsaViewDTO;
import com.wellcare.exceptions.TipoRisorsaException;
import com.wellcare.model.TipoRisorsa;
import com.wellcare.repository.tipoRisorsa.TipoRisorsaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TipoRisorsaServiceImpl implements TipoRisorsaService{
    @Autowired
    public TipoRisorsaRepository repository;


    @Override
    public List<TipoRisorsaViewDTO> getAll() throws TipoRisorsaException {
        List<TipoRisorsaViewDTO> listViewDto=new ArrayList<TipoRisorsaViewDTO>();

        List<TipoRisorsa> listEntity=repository.findAll();

        if(listEntity.isEmpty()){
            throw new TipoRisorsaException("Nessun Abbonamento trovato!");
        }
        for(TipoRisorsa a : listEntity){
            listViewDto.add(entityToDTO(a));
        }
        return listViewDto;
    }

    @Override
    public TipoRisorsaViewDTO getById(Integer id) throws TipoRisorsaException {
        TipoRisorsaViewDTO viewDTO=new TipoRisorsaViewDTO();

        Optional<TipoRisorsa> tipRis=repository.findById(id);

        if(!tipRis.isPresent()){
            throw new TipoRisorsaException("Nessun TipoRisorsa trovato!");
        }
        else {
            viewDTO=entityToDTO(tipRis.get());
        }
        return viewDTO;
    }

    private TipoRisorsaViewDTO entityToDTO(TipoRisorsa tipoRisorsa){
        TipoRisorsaViewDTO viewDTO=new TipoRisorsaViewDTO();

        viewDTO.setId(tipoRisorsa.getId());
        viewDTO.setNome(tipoRisorsa.getNome());

        return viewDTO;
    }
}
