package com.wellcare.service.schedaAlimentare;

import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareCreationDTO;
import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareViewDTO;
import com.wellcare.controller.dto.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoViewDTO;
import com.wellcare.exceptions.ContenutoSchedaAlimentareException;
import com.wellcare.model.Cibo;
import com.wellcare.model.ContenutoSchedaAlimentare;
import com.wellcare.model.ContenutoSchedaAllenamento;
import com.wellcare.model.SchedaAlimentare;
import com.wellcare.repository.cibo.CiboRepository;
import com.wellcare.repository.contenutoSchedaAlimentare.ContenutoSchedaAlimentareRepository;
import com.wellcare.repository.schedaAlimentare.SchedaAlimentareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContenutoSchedaAlimentareServiceImpl implements ContenutoSchedaAlimentareService {
    @Autowired
    public ContenutoSchedaAlimentareRepository repository;

    @Autowired
    public SchedaAlimentareRepository schedaAlimentareRepository;

    @Autowired
    public CiboRepository ciboRepository;

    @Override
    public ContenutoSchedaAlimentareViewDTO createContenutoSchedaAlimentare(ContenutoSchedaAlimentareCreationDTO contenuto,Integer idScheda) {
        ContenutoSchedaAlimentare scheda= contenutoSchedaAlimentareCreateCostructor(contenuto,idScheda);

        scheda=repository.save(scheda);

        return entityToDTO(scheda);
    }

    @Override
    public void removeContenutoSchedaAlimentare(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<ContenutoSchedaAlimentareViewDTO> getAll() throws ContenutoSchedaAlimentareException {
        List<ContenutoSchedaAlimentareViewDTO> contenutoDTO= new ArrayList<ContenutoSchedaAlimentareViewDTO>();

        List<ContenutoSchedaAlimentare> contenutoEntity= repository.findAll();

        if(contenutoEntity.isEmpty()){
            throw new ContenutoSchedaAlimentareException();
        }
        for(ContenutoSchedaAlimentare s : contenutoEntity){
            contenutoDTO.add(entityToDTO(s));
        }
        return contenutoDTO;
    }

    @Override
    public ContenutoSchedaAlimentareViewDTO getById(Integer id) throws ContenutoSchedaAlimentareException {
        ContenutoSchedaAlimentareViewDTO dto= new ContenutoSchedaAlimentareViewDTO();

        Optional<ContenutoSchedaAlimentare> entity = repository.findById(id);

        if(!entity.isPresent()){
            throw new ContenutoSchedaAlimentareException();
        }
        else {
            dto=entityToDTO(entity.get());
        }
        return dto;
    }

    @Override
    public List<ContenutoSchedaAlimentareViewDTO> findByPriorita(Integer priorita) throws ContenutoSchedaAlimentareException{
        List<ContenutoSchedaAlimentareViewDTO> listDTO= new ArrayList<ContenutoSchedaAlimentareViewDTO>();

        List<ContenutoSchedaAlimentare> entity = repository.findByPriorita(priorita);

        if(entity.isEmpty()){
            throw new ContenutoSchedaAlimentareException();
        }
        else {
            for(ContenutoSchedaAlimentare s : entity){
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    @Override
    public List<ContenutoSchedaAlimentareViewDTO> findByGiorno(Integer id, String giorno) throws ContenutoSchedaAlimentareException{
        List<ContenutoSchedaAlimentareViewDTO> listDTO= new ArrayList<ContenutoSchedaAlimentareViewDTO>();

        List<ContenutoSchedaAlimentare> entity = repository.findByGiorno(id,giorno);

        if(entity.isEmpty()){
            throw new ContenutoSchedaAlimentareException();
        }
        else {
            for(ContenutoSchedaAlimentare s : entity){
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    @Override
    public List<ContenutoSchedaAlimentareViewDTO> findBySchedaAlimentare(Integer schedaAlimentare) throws ContenutoSchedaAlimentareException {
        List<ContenutoSchedaAlimentareViewDTO> listDTO= new ArrayList<ContenutoSchedaAlimentareViewDTO>();

        List<ContenutoSchedaAlimentare> entity = repository.findByIdSchedaAlimentare(schedaAlimentare);

        if(entity.isEmpty()){
            throw new ContenutoSchedaAlimentareException("Contenuto Scheda Alimentare non trovato !");
        }
        for(ContenutoSchedaAlimentare c : entity){
            if(c.getPriorita().equalsIgnoreCase("principale")){
                ContenutoSchedaAlimentareViewDTO singoloContenutoSchedaAlimentare=new ContenutoSchedaAlimentareViewDTO();
                singoloContenutoSchedaAlimentare=entityToDTO(c);   //contenuto scheda alimentare principale

                //CERCO LE ALTERNATIVE
                List<ContenutoSchedaAlimentare> alternative=repository.findAlternativeBySchedaPrincipale(c.getId());
                if(!alternative.isEmpty()){
                    List<ContenutoSchedaAlimentareViewDTO> listaAlternativeDaAggiungereAlDTO=new ArrayList<>();

                    for (ContenutoSchedaAlimentare con : alternative){
                        listaAlternativeDaAggiungereAlDTO.add(entityToDTO(con));
                    }
                    singoloContenutoSchedaAlimentare.setAlternative(listaAlternativeDaAggiungereAlDTO); //contenuto scheda alimentare secondarie da aggiungere all' array interno della principale
                }
                listDTO.add(singoloContenutoSchedaAlimentare);    //aggiunta dei contenuti scheda alimentare riferiti a quella scheda principale
            }
        }
        return listDTO;


    }
    @Override
    public List<ContenutoSchedaAlimentareViewDTO> findAlternativeBySchedaPrincipale(Integer principale) throws ContenutoSchedaAlimentareException{
        List<ContenutoSchedaAlimentareViewDTO> listDTO= new ArrayList<ContenutoSchedaAlimentareViewDTO>();

        List<ContenutoSchedaAlimentare> entity = repository.findAlternativeBySchedaPrincipale(principale);

        if(entity.isEmpty()){
            throw new ContenutoSchedaAlimentareException();
        }
        else {
            for(ContenutoSchedaAlimentare s : entity){
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    private ContenutoSchedaAlimentare contenutoSchedaAlimentareCreateCostructor(ContenutoSchedaAlimentareCreationDTO dto,Integer idScheda){
        ContenutoSchedaAlimentare contenuto = new ContenutoSchedaAlimentare();

        contenuto.setPasto(dto.getPasto());
        contenuto.setPriorita(dto.getPriorita());
        contenuto.setQuantita(dto.getQuantita());

        Optional<SchedaAlimentare> schedaAlimentareOpt=schedaAlimentareRepository.findById(idScheda);
        if(schedaAlimentareOpt.isPresent()){
            contenuto.setSchedaAlimentare(schedaAlimentareOpt.get());
        }

        Optional<Cibo> ciboOptional=ciboRepository.findById(dto.getCibo());
        if(ciboOptional.isPresent()){
            contenuto.setCibo(ciboOptional.get());
        }

        contenuto.setPrincipale(dto.getPrincipale());

        return contenuto;
    }


    private List<ContenutoSchedaAlimentareViewDTO> entityListToDTOList(List<ContenutoSchedaAlimentare> entityList){
        ContenutoSchedaAlimentareViewDTO dto= new ContenutoSchedaAlimentareViewDTO();
        List<ContenutoSchedaAlimentareViewDTO> alternativeListView = new ArrayList<>();

        for (ContenutoSchedaAlimentare entity: entityList) {
            dto.setId(entity.getId());
            dto.setSchedaAlimentare(entity.getSchedaAlimentare().getId());
            dto.setCibo(entity.getCibo().getId());
            dto.setPasto(entity.getPasto());
            dto.setPriorita(entity.getPriorita());
            dto.setPrincipale(entity.getPrincipale());
            dto.setQuantita(entity.getQuantita());
            dto.setGiorno(entity.getGiorno());
            dto.setNomeCibo(entity.getCibo().getNome());
            if (entity.getGiorno() == 1) {
                dto.setNomeGiorno("Lunedi");
            } else if (entity.getGiorno() == 2) {
                dto.setNomeGiorno("Martedi");
            } else if (entity.getGiorno() == 3) {
                dto.setNomeGiorno("Mercoledi");
            } else if (entity.getGiorno() == 4) {
                dto.setNomeGiorno("Giovedi");
            } else if (entity.getGiorno() == 5) {
                dto.setNomeGiorno("Venerdi");
            } else if (entity.getGiorno() == 6) {
                dto.setNomeGiorno("Sabato");
            } else if (entity.getGiorno() == 7) {
                dto.setNomeGiorno("Domenica");
            }

            alternativeListView.add(dto);

        }

        return alternativeListView;
    }

    private ContenutoSchedaAlimentareViewDTO entityToDTO(ContenutoSchedaAlimentare entity){
        ContenutoSchedaAlimentareViewDTO dto= new ContenutoSchedaAlimentareViewDTO();
        dto.setId(entity.getId());
        dto.setSchedaAlimentare(entity.getSchedaAlimentare().getId());
        dto.setCibo(entity.getCibo().getId());
        dto.setPasto(entity.getPasto());
        dto.setPriorita(entity.getPriorita());
        dto.setPrincipale(entity.getPrincipale());
        dto.setQuantita(entity.getQuantita());
        dto.setGiorno(entity.getGiorno());
        dto.setNomeCibo(entity.getCibo().getNome());
        if(entity.getGiorno() == 1){
            dto.setNomeGiorno("Lunedi");
        } else if(entity.getGiorno() == 2){
            dto.setNomeGiorno("Martedi");
        } else if(entity.getGiorno() == 3){
            dto.setNomeGiorno("Mercoledi");
        } else if(entity.getGiorno() == 4){
            dto.setNomeGiorno("Giovedi");
        } else if(entity.getGiorno() == 5){
            dto.setNomeGiorno("Venerdi");
        } else if(entity.getGiorno() == 6){
            dto.setNomeGiorno("Sabato");
        } else if(entity.getGiorno() == 7){
            dto.setNomeGiorno("Domenica");
        }
        List<ContenutoSchedaAlimentare> alternatives = repository.findAlternativeBySchedaPrincipale(entity.getId());
        dto.setAlternative(entityListToDTOList(alternatives));

        return dto;
    }
}