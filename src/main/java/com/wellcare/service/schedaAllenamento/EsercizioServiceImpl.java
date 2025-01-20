package com.wellcare.service.schedaAllenamento;

import com.wellcare.controller.dto.esercizio.EsercizioCreationDTO;
import com.wellcare.controller.dto.esercizio.EsercizioViewDTO;
import com.wellcare.model.Esercizio;
import com.wellcare.repository.esercizio.EsercizioRepository;
import com.wellcare.exceptions.EsercizioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EsercizioServiceImpl implements EsercizioService {

    @Autowired
    public EsercizioRepository repository;



    @Override
    public EsercizioViewDTO createEsercizio(EsercizioCreationDTO esercizioCreaDTO) {
        Esercizio esercizio=esercizioCreateCostructor(esercizioCreaDTO);
        esercizio=repository.save(esercizio);
        return entityToDTO(esercizio);
    }

    @Override
    public void removeEsercizio(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<EsercizioViewDTO> findAll() throws EsercizioException{
        List<EsercizioViewDTO> listViewDto= new ArrayList<EsercizioViewDTO>();

        List<Esercizio> listEntity = repository.findAll();

        if(listEntity.isEmpty()){
            throw new EsercizioException("Nessun Esercizio trovato!");
        }
        for(Esercizio e : listEntity){
            listViewDto.add(entityToDTO(e));
        }
        return listViewDto;
    }

    @Override
    public EsercizioViewDTO findById(Integer id) throws EsercizioException{
        EsercizioViewDTO esercizioViewDTO= new EsercizioViewDTO();

        Optional<Esercizio> entity=repository.findById(id);

        if(!entity.isPresent()){
            throw new EsercizioException("Nessun Esercizio trovato!");
        }
        esercizioViewDTO=entityToDTO(entity.get());
        return esercizioViewDTO;
    }

    @Override
    public List<EsercizioViewDTO> findByNome(String nome) throws EsercizioException{
        List<EsercizioViewDTO> listViewDto= new ArrayList<EsercizioViewDTO>();

        List<Esercizio> listEntity = repository.findByNome(nome);

        if(listEntity.isEmpty()){
            throw new EsercizioException("Nessun Esercizio trovato!");
        }
        for(Esercizio e : listEntity){
            listViewDto.add(entityToDTO(e));
        }
        return listViewDto;
    }

    @Override
    public String findDescrizione(Integer id) {
        String descrizioneEsercizio;

        descrizioneEsercizio=repository.findDescrizione(id);

        return descrizioneEsercizio;
    }

    private Esercizio esercizioCreateCostructor(EsercizioCreationDTO esercizioCreationDTO){
        Esercizio esercizio= new Esercizio();

        esercizio.setDescrizione(esercizioCreationDTO.getDescrizione());
        esercizio.setNome(esercizioCreationDTO.getNome());

        return esercizio;
    }
    private EsercizioViewDTO entityToDTO(Esercizio entity){
        EsercizioViewDTO esercizioViewDTO=new EsercizioViewDTO();

        esercizioViewDTO.setId(entity.getId());
        esercizioViewDTO.setNome(entity.getNome());
        esercizioViewDTO.setDescrizione(entity.getDescrizione());

        return esercizioViewDTO;
    }
}
