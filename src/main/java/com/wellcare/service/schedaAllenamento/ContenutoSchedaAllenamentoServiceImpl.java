package com.wellcare.service.schedaAllenamento;

import com.wellcare.controller.dto.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoCreationDTO;
import com.wellcare.controller.dto.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoViewDTO;
import com.wellcare.model.ContenutoSchedaAllenamento;
import com.wellcare.model.Esercizio;
import com.wellcare.model.SchedaAllenamento;
import com.wellcare.repository.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoRepository;
import com.wellcare.exceptions.ContenutoSchedaAllenamentoException;
import com.wellcare.repository.esercizio.EsercizioRepository;
import com.wellcare.repository.schedaAllenamento.SchedaAllenamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContenutoSchedaAllenamentoServiceImpl implements ContenutoSchedaAllenamentoService {


    @Autowired
    public ContenutoSchedaAllenamentoRepository repository;

    @Autowired
    public SchedaAllenamentoRepository schedaAllenamentoRepository;

    @Autowired
    public EsercizioRepository esercizioRepository;

    public ContenutoSchedaAllenamentoViewDTO createContenutoSchedaAllenamento(ContenutoSchedaAllenamentoCreationDTO creationDTO,Integer idScheda){
        ContenutoSchedaAllenamento contenutoSchedaAllenamento=createCostructorContenutoSchedaAllenamento(creationDTO,idScheda);

        contenutoSchedaAllenamento=repository.save(contenutoSchedaAllenamento);

        return entityToDTO(contenutoSchedaAllenamento);
    }

    @Override
    public void removeContenutoSchedaAllenamento(Integer id) {

        repository.deleteById(id);

    }

    @Override
    public List<ContenutoSchedaAllenamentoViewDTO> findAll() throws ContenutoSchedaAllenamentoException{
        List<ContenutoSchedaAllenamentoViewDTO> listViewDto=new ArrayList<ContenutoSchedaAllenamentoViewDTO>();

        List<ContenutoSchedaAllenamento> listEntity= repository.findAll();
        if(listEntity.isEmpty()){
            throw new ContenutoSchedaAllenamentoException("Contenuto Scheda Allenamento non trovato !");
        }
        for(ContenutoSchedaAllenamento c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }

    @Override
    public ContenutoSchedaAllenamentoViewDTO findById(Integer id) throws ContenutoSchedaAllenamentoException{
        ContenutoSchedaAllenamentoViewDTO viewDTO=new ContenutoSchedaAllenamentoViewDTO();

        Optional<ContenutoSchedaAllenamento> entity=repository.findById(id);

        if(!entity.isPresent()){
            throw new ContenutoSchedaAllenamentoException("Contenuto Scheda Allenamento non trovato !");
        }
        else {
            viewDTO=entityToDTO(entity.get());
        }
        return viewDTO;
    }

    public List<ContenutoSchedaAllenamentoViewDTO> findByPriorità(Integer prio) throws ContenutoSchedaAllenamentoException{
        List<ContenutoSchedaAllenamentoViewDTO> listViewDto=new ArrayList<ContenutoSchedaAllenamentoViewDTO>();

        List<ContenutoSchedaAllenamento> listEntity= repository.findByPriorità(prio);
        if(listEntity.isEmpty()){
            throw new ContenutoSchedaAllenamentoException("Contenuto Scheda Allenamento non trovato !");
        }
        for(ContenutoSchedaAllenamento c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }

    public List<ContenutoSchedaAllenamentoViewDTO> findByGiorno(Integer idScheda,Integer giorno) throws ContenutoSchedaAllenamentoException{
        List<ContenutoSchedaAllenamentoViewDTO> listViewDto=new ArrayList<ContenutoSchedaAllenamentoViewDTO>();

        List<ContenutoSchedaAllenamento> listEntity= repository.findByGiorno(idScheda,giorno);
        if(listEntity.isEmpty()){
            throw new ContenutoSchedaAllenamentoException("Contenuto Scheda Allenamento non trovato !");
        }
        for(ContenutoSchedaAllenamento c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }

    public List<ContenutoSchedaAllenamentoViewDTO> findBySchedaAllenamento(Integer idSchedaAllenamento) throws ContenutoSchedaAllenamentoException{
        List<ContenutoSchedaAllenamentoViewDTO> listViewDto=new ArrayList<ContenutoSchedaAllenamentoViewDTO>();

        List<ContenutoSchedaAllenamento> listEntity= repository.findByIdSchedaAllenamento(idSchedaAllenamento);
        if(listEntity.isEmpty()){
            throw new ContenutoSchedaAllenamentoException("Contenuto Scheda Allenamento non trovato !");
        }
        for(ContenutoSchedaAllenamento c : listEntity){
            if(c.getPriorita().equalsIgnoreCase("principale")){
                ContenutoSchedaAllenamentoViewDTO singoloContenutoSchedaAllenamento=new ContenutoSchedaAllenamentoViewDTO();
                singoloContenutoSchedaAllenamento=entityToDTO(c);

                //CERCO LE ALTERNATIVE
                List<ContenutoSchedaAllenamento> alternative=repository.findAlternativeBySchedaPrincipale(c.getId());
                if(!alternative.isEmpty()){
                    List<ContenutoSchedaAllenamentoViewDTO> listaAlternativeDaAggiungereAlDTO=new ArrayList<>();
                    for (ContenutoSchedaAllenamento con : alternative){
                        listaAlternativeDaAggiungereAlDTO.add(entityToDTO(con));
                    }
                    singoloContenutoSchedaAllenamento.setAlternative(listaAlternativeDaAggiungereAlDTO);
                }
            listViewDto.add(singoloContenutoSchedaAllenamento);
            }

        }
        return listViewDto;
    }

    public List<ContenutoSchedaAllenamentoViewDTO> findAlternativeBySchedaPrincipale(Integer idContenutoPrincipale) throws ContenutoSchedaAllenamentoException{
        List<ContenutoSchedaAllenamentoViewDTO> listViewDto=new ArrayList<ContenutoSchedaAllenamentoViewDTO>();

        List<ContenutoSchedaAllenamento> listEntity= repository.findAlternativeBySchedaPrincipale(idContenutoPrincipale);
        if(listEntity.isEmpty()){
            throw new ContenutoSchedaAllenamentoException("Contenuto Scheda Allenamento non trovato !");
        }
        for(ContenutoSchedaAllenamento c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }


    private ContenutoSchedaAllenamento createCostructorContenutoSchedaAllenamento(ContenutoSchedaAllenamentoCreationDTO dto,Integer idScheda){
        ContenutoSchedaAllenamento entity= new ContenutoSchedaAllenamento();

        entity.setPriorita(dto.getPriorita());
        entity.setRipetizioni(dto.getRipetizioni());
        entity.setGiorno(dto.getGiorno());

        Optional<Esercizio> esOpt=esercizioRepository.findById(dto.getEsercizio());
        if(esOpt.isPresent()){
            entity.setEsercizio(esOpt.get());
        }
        Optional<SchedaAllenamento> scOpt=schedaAllenamentoRepository.findById(idScheda);
        if(scOpt.isPresent()){
            entity.setSchedaAllenamento(scOpt.get());
        }

        entity.setPrincipale(dto.getPrincipale());

        return entity;
    }
    private ContenutoSchedaAllenamentoViewDTO entityToDTO(ContenutoSchedaAllenamento entity){
        ContenutoSchedaAllenamentoViewDTO viewDto= new ContenutoSchedaAllenamentoViewDTO();

        viewDto.setId(entity.getId());
        viewDto.setPriorita(entity.getPriorita());
        viewDto.setRipetizioni(entity.getRipetizioni());
        viewDto.setGiorno(entity.getGiorno());
        viewDto.setEsercizio(entity.getEsercizio().getId());
        viewDto.setSchedaAllenamento(entity.getSchedaAllenamento().getId());
        viewDto.setPrincipale(entity.getPrincipale());
        viewDto.setGiorno(entity.getGiorno());
        viewDto.setNomeEsercizio(entity.getEsercizio().getNome());

        return viewDto;
    }
}
