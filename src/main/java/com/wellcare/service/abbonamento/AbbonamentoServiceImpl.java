package com.wellcare.service.abbonamento;

import com.wellcare.controller.dto.abbonamento.AbbonamentoViewDTO;
import com.wellcare.model.Abbonamento;
import com.wellcare.repository.abbonamento.AbbonamentoRepository;
import com.wellcare.exceptions.AbbonamentoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AbbonamentoServiceImpl implements AbbonamentoService {

    @Autowired
    public AbbonamentoRepository repository;


    @Override
    public List<AbbonamentoViewDTO> getAll() throws AbbonamentoException {
        List<AbbonamentoViewDTO> listViewDto=new ArrayList<AbbonamentoViewDTO>();

        List<Abbonamento> listEntity=repository.findAll();

        if(listEntity.isEmpty()){
            throw new AbbonamentoException("Nessun Abbonamento trovato!");
        }
        for(Abbonamento a : listEntity){
            listViewDto.add(entityToDTO(a));
        }
        return listViewDto;
    }

    @Override
    public AbbonamentoViewDTO getById(Integer id) throws AbbonamentoException {
        AbbonamentoViewDTO viewDTO=new AbbonamentoViewDTO();

        Optional<Abbonamento> abb=repository.findById(id);

        if(!abb.isPresent()){
            throw new AbbonamentoException("Nessun Abbonamento trovato!");
        }
        else {
            viewDTO=entityToDTO(abb.get());
        }
        return viewDTO;
    }

    private AbbonamentoViewDTO entityToDTO(Abbonamento abbonamento){
        AbbonamentoViewDTO viewDTO=new AbbonamentoViewDTO();

        viewDTO.setId(abbonamento.getId());
        viewDTO.setNome(abbonamento.getNome());
        viewDTO.setDescrizione(abbonamento.getDescrizione());
        viewDTO.setNumMaxCliente(abbonamento.getNumMaxCliente());
        viewDTO.setPrezzo(abbonamento.getPrezzo());

        return viewDTO;
    }
    /*private Abbonamento createAbbonamentoCostructor(AbbonamentoCreationDTO dto){
        Abbonamento abbonamento=new Abbonamento();

        abbonamento.setDescrizione(dto.getDescrizione());
        abbonamento.setNome(dto.getNome());
        abbonamento.setPrezzo(dto.getPrezzo());
        abbonamento.setNumMaxCliente(dto.getNumMaxCliente());

        return abbonamento;
    }*/
}
