package com.wellcare.service.pastiGiornalieri;

import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroCreationDTO;
import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroUpdateDTO;
import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroViewDTO;
import com.wellcare.exceptions.PastiGiornalieriException;
import com.wellcare.model.*;
import com.wellcare.repository.cibo.CiboRepository;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.repository.pastiGiornalieri.PastiGiornalieriRepository;
import com.wellcare.repository.schedaAlimentare.SchedaAlimentareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PastiGiornalieriServiceImpl implements PastiGiornalieriService {

    @Autowired
    public PastiGiornalieriRepository repository;
    @Autowired
    public ClienteRepository clienteRepository;
    @Autowired
    public SchedaAlimentareRepository schedaAlimentareRepository;
    @Autowired
    public CiboRepository ciboRepository;

    @Override
    public PastoGiornalieroViewDTO createPastoGiornaliero(PastoGiornalieroCreationDTO dto) {
        PastoGiornaliero pastoGiornaliero=createCostructorPastoGiornaliero(dto);

        pastoGiornaliero=repository.save(pastoGiornaliero);

        return entityToDTO(pastoGiornaliero);
    }

    @Override
    public void removePastoGiornaliero(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public PastoGiornalieroViewDTO updatePastoGiornaliero(PastoGiornalieroUpdateDTO dto, Integer idRiga) throws PastiGiornalieriException {
        Optional<PastoGiornaliero> cbOnt=repository.findById(idRiga);

        if(cbOnt.isPresent()){
            cbOnt.get().setData(dto.getData());
            cbOnt.get().setQuantita(dto.getQuantita());
            cbOnt.get().setPasto(dto.getPasto());


            Optional<Cibo> ciboOptional=ciboRepository.findById(dto.getIdCibo());
            if (ciboOptional.isPresent()){
                cbOnt.get().setCibo(ciboOptional.get());
            }
            else {
                throw new PastiGiornalieriException("Pasto non trovato!");
            }

            repository.save(cbOnt.get());
        }
        else {
            throw new PastiGiornalieriException("Pasto Giornaliero non trovato ! ");
        }
        return entityToDTO(cbOnt.get());
    }

    @Override
    public List<PastoGiornalieroViewDTO> findAll() throws PastiGiornalieriException {
        List<PastoGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<PastoGiornaliero> listEntity=repository.findAll();
        if(listEntity.isEmpty()){
            throw new PastiGiornalieriException("Pasti Giornalieri non trovati!");
        }
        else {
            for (PastoGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }
        return listViewDTO;
    }

    @Override
    public PastoGiornalieroViewDTO findById(Integer id) throws PastiGiornalieriException {
        PastoGiornalieroViewDTO viewDTO=new PastoGiornalieroViewDTO();

        Optional<PastoGiornaliero> cbOnt=repository.findById(id);

        if(!cbOnt.isPresent()){
            throw new PastiGiornalieriException("Pasto Giornaliero non trovato!");
        }
        else {
            viewDTO=entityToDTO(cbOnt.get());
        }
        return viewDTO;
    }

    @Override
    public List<PastoGiornalieroViewDTO> findByGiorno(Integer idScheda, Integer giorno) throws PastiGiornalieriException {
        List<PastoGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<PastoGiornaliero> listEntity=repository.findByGiorno(idScheda,giorno);

        if(listEntity.isEmpty()){
            throw new PastiGiornalieriException("Esercizi Giornalieri non trovati!");
        }
        else {
            for (PastoGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }
        return listViewDTO;
    }

    @Override
    public List<PastoGiornalieroViewDTO> findBySchedaAlimentare(Integer idSchedaAlimentare) throws PastiGiornalieriException {
        List<PastoGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<PastoGiornaliero> listEntity=repository.findBySchedaAlimentare(idSchedaAlimentare);

        if(listEntity.isEmpty()){
            throw new PastiGiornalieriException("Pasti Giornalieri non trovati!");
        }
        else {
            for (PastoGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }
        return listViewDTO;
    }

    @Override
    public List<PastoGiornalieroViewDTO> findByIdCliente(Integer idCliente) throws PastiGiornalieriException {
        List<PastoGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<PastoGiornaliero> listEntity=repository.findByIdCliente(idCliente);

        if(listEntity.isEmpty()){
            throw new PastiGiornalieriException("Pasti Giornalieri non trovati!");
        }
        else {
            for (PastoGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }
        return listViewDTO;
    }

    @Override
    public List<PastoGiornalieroViewDTO> findByDataCreazione(Integer id, String data)  {
        List<PastoGiornalieroViewDTO> listViewDTO=new ArrayList<>();

        List<PastoGiornaliero> listEntity=repository.findByDataCreazione(id,data);

        if(!listEntity.isEmpty()){
            for (PastoGiornaliero e : listEntity){
                listViewDTO.add(entityToDTO(e));
            }
        }

        return listViewDTO;
    }

    private PastoGiornaliero createCostructorPastoGiornaliero(PastoGiornalieroCreationDTO dto){
        PastoGiornaliero entity= new PastoGiornaliero();

        entity.setData(dto.getData());
        entity.setPasto(dto.getPasto());
        entity.setQuantita(dto.getQuantita());

        Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());
        if(clOpt.isPresent()){
            entity.setCliente(clOpt.get());
        }
        Optional<Cibo> cbOnt=ciboRepository.findById(dto.getIdCibo());
        if(cbOnt.isPresent()){
            entity.setCibo(cbOnt.get());
        }
        Optional<SchedaAlimentare> scOnt=schedaAlimentareRepository.findById(dto.getIdSchedaAlimentare());
        if(scOnt.isPresent()){
            entity.setSchedaAlimentare(scOnt.get());
        }

        return entity;
    }

    private PastoGiornalieroViewDTO entityToDTO(PastoGiornaliero entity){
        PastoGiornalieroViewDTO viewDto= new PastoGiornalieroViewDTO();

        viewDto.setId(entity.getId());
        viewDto.setData(entity.getData());
        viewDto.setPasto(entity.getPasto());
        viewDto.setQuantita(entity.getQuantita());
        viewDto.setIdCliente(entity.getCliente().getId());
        viewDto.setIdCibo(entity.getCibo().getId());
        viewDto.setNomeCibo(entity.getCibo().getNome());
        viewDto.setIdSchedaAlimentare(entity.getSchedaAlimentare().getId());

        return viewDto;
    }
}
