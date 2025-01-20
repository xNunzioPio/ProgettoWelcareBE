package com.wellcare.service.schedaAlimentare;

import com.wellcare.controller.dto.cibo.CiboCreationDTO;
import com.wellcare.controller.dto.cibo.CiboViewDTO;
import com.wellcare.model.Cibo;
import com.wellcare.repository.cibo.CiboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CiboServiceImpl implements CiboService {

    @Autowired
    public CiboRepository repository;

    @Override
    public CiboViewDTO createCibo(CiboCreationDTO ciboDTO) {
        Cibo cibo= ciboCreateCostructor(ciboDTO);
        cibo=repository.save(cibo);
        return entityToDTO(cibo);
    }

    @Override
    public void removeCibo(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<CiboViewDTO> getAll() {
        List<CiboViewDTO> schedeDTO= new ArrayList<CiboViewDTO>();
        List<Cibo> schedeEntity= repository.findAll();

        if(schedeEntity.isEmpty()){
            throw new RuntimeException();
        }
        for(Cibo s : schedeEntity){
            schedeDTO.add(entityToDTO(s));
        }
        return schedeDTO;
    }

    @Override
    public CiboViewDTO getById(Integer id) {
        CiboViewDTO dto= new CiboViewDTO();
        Optional<Cibo> entity = repository.findById(id);

        if(!entity.isPresent()){
            throw new RuntimeException();
        }
        else {
            dto=entityToDTO(entity.get());
        }
        return dto;
    }

    @Override
    public List<CiboViewDTO> findByNome(String nome) {
        List<CiboViewDTO> listDTO= new ArrayList<CiboViewDTO>();

        List<Cibo> entity = repository.findByNome(nome);

        if(entity.isEmpty()){
            throw new RuntimeException();
        }
        else {
            for(Cibo s : entity){
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    private CiboViewDTO entityToDTO(Cibo entity){
        CiboViewDTO dto= new CiboViewDTO();

        dto.setId(entity.getId());
        dto.setNome(entity.getNome());
        return dto;
    }

    private Cibo ciboCreateCostructor(CiboCreationDTO dto){
        Cibo cibo = new Cibo();
        cibo.setNome(dto.getNome());
        return cibo;
    }
}
