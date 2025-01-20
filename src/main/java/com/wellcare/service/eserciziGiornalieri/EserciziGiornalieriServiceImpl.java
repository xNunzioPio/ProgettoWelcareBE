package com.wellcare.service.eserciziGiornalieri;

import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroCreationDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroUpdateDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroViewDTO;
import com.wellcare.exceptions.EserciziGiornalieriException;
import com.wellcare.model.Cliente;
import com.wellcare.model.Esercizio;
import com.wellcare.model.EsercizioGiornaliero;
import com.wellcare.model.SchedaAllenamento;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.repository.eserciziGiornalieri.EserciziGiornalieriRepository;
import com.wellcare.repository.esercizio.EsercizioRepository;
import com.wellcare.repository.schedaAllenamento.SchedaAllenamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EserciziGiornalieriServiceImpl implements EserciziGiornalieriService{

    @Autowired
    public EserciziGiornalieriRepository repository;
    @Autowired
    public ClienteRepository clienteRepository;
    @Autowired
    public SchedaAllenamentoRepository schedaAllenamentoRepository;
    @Autowired
    public EsercizioRepository esercizioRepository;

    public EserciziGiornalieriServiceImpl(EserciziGiornalieriRepository es,ClienteRepository cl,SchedaAllenamentoRepository sh,EsercizioRepository e){
        this.repository=es;
        this.clienteRepository=cl;
        this.schedaAllenamentoRepository=sh;
        this.esercizioRepository=e;
    }

    @Override
    public EsercizioGiornalieroViewDTO createEsercizioGiornaliero(EsercizioGiornalieroCreationDTO dto) throws EserciziGiornalieriException {
        EsercizioGiornaliero esercizioGiornaliero=createCostructorEsercizioGiornaliero(dto);

        esercizioGiornaliero=repository.save(esercizioGiornaliero);

        return entityToDTO(esercizioGiornaliero);
    }

    @Override
    public void removeEsercizioGiornaliero(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public EsercizioGiornalieroViewDTO updateEsercizioGiornaliero(EsercizioGiornalieroUpdateDTO dto, Integer idRiga) throws EserciziGiornalieriException {
        Optional<EsercizioGiornaliero> esOpt=repository.findById(idRiga);

        if(esOpt.isPresent()){
            esOpt.get().setData(dto.getData());
            esOpt.get().setRipetizioni(dto.getRipetizioni());

            Optional<Esercizio> esercizioOptional=esercizioRepository.findById(dto.getIdEsercizio());
            if (esercizioOptional.isPresent()){
                esOpt.get().setEsercizio(esercizioOptional.get());
            }
            else {
                throw new EserciziGiornalieriException("Esercizio non trovato!");
            }

            repository.save(esOpt.get());
        }
        else {
            throw new EserciziGiornalieriException("Esercizio Giornaliero non trovato ! ");
        }
        return entityToDTO(esOpt.get());
    }

    @Override
    public List<EsercizioGiornalieroViewDTO> findAll() throws EserciziGiornalieriException {
        List<EsercizioGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<EsercizioGiornaliero> listEntity=repository.findAll();
        if(listEntity.isEmpty()){
            throw new EserciziGiornalieriException("Esercizi Giornalieri non trovati!");
        }
        else {
            for (EsercizioGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }
        return listViewDTO;
    }

    @Override
    public EsercizioGiornalieroViewDTO findById(Integer id) throws EserciziGiornalieriException {
        EsercizioGiornalieroViewDTO viewDTO=new EsercizioGiornalieroViewDTO();

        Optional<EsercizioGiornaliero> esOpt=repository.findById(id);

        if(!esOpt.isPresent()){
            throw new EserciziGiornalieriException("Esercizio Giornaliero non trovato!");
        }
        else {
            viewDTO=entityToDTO(esOpt.get());
        }
        return viewDTO;
    }

    @Override
    public List<EsercizioGiornalieroViewDTO> findByGiorno(Integer idScheda, Integer giorno) throws EserciziGiornalieriException {
        List<EsercizioGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<EsercizioGiornaliero> listEntity=repository.findByGiorno(idScheda,giorno);

        if(listEntity.isEmpty()){
            throw new EserciziGiornalieriException("Esercizi Giornalieri non trovati!");
        }
        else {
            for (EsercizioGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }
        return listViewDTO;
    }

    @Override
    public List<EsercizioGiornalieroViewDTO> findBySchedaAllenamento(Integer idSchedaAllenamento) throws EserciziGiornalieriException {
        List<EsercizioGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<EsercizioGiornaliero> listEntity=repository.findBySchedaAllenamento(idSchedaAllenamento);

        if(listEntity.isEmpty()){
            throw new EserciziGiornalieriException("Esercizi Giornalieri non trovati!");
        }
        else {
            for (EsercizioGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }
        return listViewDTO;
    }

    @Override
    public List<EsercizioGiornalieroViewDTO> findByIdCliente(Integer idcliente) throws EserciziGiornalieriException {
        List<EsercizioGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<EsercizioGiornaliero> listEntity=repository.findByIdCliente(idcliente);

        if(listEntity.isEmpty()){
            throw new EserciziGiornalieriException("Esercizi Giornalieri non trovati!");
        }
        else {
            for (EsercizioGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }
        return listViewDTO;
    }
    @Override
    public List<EsercizioGiornalieroViewDTO> findByDataCreazione(Integer id, String data) {
        List<EsercizioGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<EsercizioGiornaliero> listEntity=repository.findByDataCreazione(id, data);

        if(!listEntity.isEmpty()){
            for (EsercizioGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }

        return listViewDTO;
    }

    private EsercizioGiornaliero createCostructorEsercizioGiornaliero(EsercizioGiornalieroCreationDTO dto) throws EserciziGiornalieriException {
        EsercizioGiornaliero entity= new EsercizioGiornaliero();

        if(dto.getData() == null)
            throw new EserciziGiornalieriException("Data non valida!");
        else
            entity.setData(dto.getData());

        if(dto.getRipetizioni() == null || dto.getRipetizioni() <1 )
            throw new EserciziGiornalieriException("Ripetizioni non valide!");
        else
            entity.setRipetizioni(dto.getRipetizioni());

        if(dto.getIdCliente() == null || dto.getIdCliente() <1)
            throw new EserciziGiornalieriException("Id cliente non valido!");
        else {
            Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());
            if(clOpt.isPresent()){
                entity.setCliente(clOpt.get());
            }
        }

        if(dto.getIdEsercizio() == null || dto.getIdEsercizio() <1)
            throw new EserciziGiornalieriException("Id esercizio non valido!");
        else {
            Optional<Esercizio> esOpt=esercizioRepository.findById(dto.getIdEsercizio());
            if(esOpt.isPresent()){
                entity.setEsercizio(esOpt.get());
            }
        }

        if(dto.getIdSchedaAllenamento() == null || dto.getIdSchedaAllenamento() < 1)
            throw new EserciziGiornalieriException("Id scheda allenamento non valida!");
        else {
            Optional<SchedaAllenamento> scOpt=schedaAllenamentoRepository.findById(dto.getIdSchedaAllenamento());
            if(scOpt.isPresent()){
                entity.setSchedaAllenamento(scOpt.get());
            }
        }


        return entity;
    }
    public EsercizioGiornalieroViewDTO entityToDTO(EsercizioGiornaliero entity){
        EsercizioGiornalieroViewDTO viewDto= new EsercizioGiornalieroViewDTO();

        viewDto.setId(entity.getId());
        viewDto.setData(entity.getData());
        viewDto.setRipetizioni(entity.getRipetizioni());
        viewDto.setIdCliente(entity.getCliente().getId());
        viewDto.setIdEsercizio(entity.getEsercizio().getId());
        viewDto.setNomeEsercizio(entity.getEsercizio().getNome());
        viewDto.setIdSchedaAllenamento(entity.getSchedaAllenamento().getId());

        return viewDto;
    }


}
