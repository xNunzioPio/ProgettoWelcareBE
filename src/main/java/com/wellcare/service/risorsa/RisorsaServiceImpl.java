package com.wellcare.service.risorsa;


import com.wellcare.controller.dto.risorsa.RisorsaCreationDTO;
import com.wellcare.controller.dto.risorsa.RisorsaViewDTO;
import com.wellcare.exceptions.RisorsaException;
import com.wellcare.model.*;
import com.wellcare.repository.risorsa.RisorsaRepository;
import com.wellcare.repository.tipoRisorsa.TipoRisorsaRepository;
import com.wellcare.repository.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RisorsaServiceImpl implements RisorsaService {

    @Autowired
    public RisorsaRepository repository;

    @Autowired
    public TipoRisorsaRepository tipoRisorsarepository;

    @Autowired
    public UtenteRepository utenterepository;



    @Override
    public RisorsaViewDTO createRisorsa(RisorsaCreationDTO dto) {
        Risorsa risorsa=createRisorsaCostructor(dto);

        risorsa=repository.save(risorsa);

        return entityToDTO(risorsa);
    }

    @Override
    public void removeRisorsa(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<RisorsaViewDTO> findByIdUtente(Integer idUtente) {
        List<RisorsaViewDTO> listDTO = new ArrayList<RisorsaViewDTO>();

        List<Risorsa> entity = repository.findByIdUtente(idUtente);

        if (entity.isEmpty()) {
            throw new RuntimeException();
        } else {
            for (Risorsa s : entity) {
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    @Override
    public List<RisorsaViewDTO> findByIdTipo(Integer idTipo) {
        List<RisorsaViewDTO> listDTO = new ArrayList<RisorsaViewDTO>();

        List<Risorsa> entity = repository.findByIdTipo(idTipo);

        if (entity.isEmpty()) {
            throw new RuntimeException();
        } else {
            for (Risorsa s : entity) {
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    @Override
    public List<RisorsaViewDTO> findByPath(String path) {
        List<RisorsaViewDTO> listDTO = new ArrayList<RisorsaViewDTO>();

        List<Risorsa> entity = repository.findByPath(path);

        if (entity.isEmpty()) {
            throw new RuntimeException();
        } else {
            for (Risorsa s : entity) {
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    @Override
    public List<RisorsaViewDTO> findAll() throws RisorsaException {
        List<RisorsaViewDTO> listDto=new ArrayList<RisorsaViewDTO>();

        List<Risorsa> listEntity=repository.findAll();

        if(listEntity.isEmpty()){
            throw new RisorsaException("Nessuna Risorsa trovato!");
        }
        for (Risorsa p : listEntity){
            listDto.add(entityToDTO(p));
        }
        return listDto;
    }

    @Override
    public RisorsaViewDTO findById(Integer id) throws RisorsaException {
        RisorsaViewDTO dto=new RisorsaViewDTO();

        Optional<Risorsa> entity=repository.findById(id);

        if(!entity.isPresent()){
            throw new RisorsaException("Nessuna Risorsa trovata!");
        }
        else {
            dto=entityToDTO(entity.get());
        }
        return dto;
    }

    private Risorsa createRisorsaCostructor(RisorsaCreationDTO dto){
        Risorsa entity=new Risorsa();

        Optional<Utente> ptOpt=utenterepository.findById(dto.getIdUtente());
        if (ptOpt.isPresent()){
            entity.setUtente(ptOpt.get());
        }
        Optional<TipoRisorsa> abOpt=tipoRisorsarepository.findById(dto.getIdTipo());
        if(abOpt.isPresent()){
            entity.setTipoRisorsa(abOpt.get());
        }

        entity.setNome(dto.getNome());
        entity.setDescrizione(dto.getDescrizione());
        entity.setPath(dto.getPath());

        return entity;
    }

    private RisorsaViewDTO entityToDTO(Risorsa risorsa){
        RisorsaViewDTO viewDTO=new RisorsaViewDTO();

        viewDTO.setId(risorsa.getId());
        viewDTO.setNome(risorsa.getNome());
        viewDTO.setDescrizione(risorsa.getDescrizione());
        viewDTO.setPath(risorsa.getPath());
        viewDTO.setIdUtente(risorsa.getUtente().getId());
        viewDTO.setIdTipo(risorsa.getTipoRisorsa().getId());


        return viewDTO;
    }
}
