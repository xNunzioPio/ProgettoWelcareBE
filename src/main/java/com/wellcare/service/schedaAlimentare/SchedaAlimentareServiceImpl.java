package com.wellcare.service.schedaAlimentare;

import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.contenutoSchedaAlimentare.ContenutoSchedaAlimentareCreationDTO;
import com.wellcare.controller.dto.schedaAlimentare.SchedaAlimentareCreationDTO;
import com.wellcare.controller.dto.schedaAlimentare.SchedaAlimentareViewDTO;
import com.wellcare.exceptions.ContenutoSchedaAlimentareException;
import com.wellcare.exceptions.SchedaAlimentareException;
import com.wellcare.model.*;
import com.wellcare.repository.cibo.CiboRepository;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.repository.contenutoSchedaAlimentare.ContenutoSchedaAlimentareRepository;
import com.wellcare.repository.nutrizionista.NutrizionistaRepository;
import com.wellcare.repository.schedaAlimentare.SchedaAlimentareRepository;
import com.wellcare.repository.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SchedaAlimentareServiceImpl implements SchedaAlimentareService {

    public SchedaAlimentareRepository repository;
    @Autowired
    public ContenutoSchedaAlimentareRepository repositoryContenutoScheda;
    @Autowired
    public CiboRepository ciboRepository;
    @Autowired
    public ClienteRepository clienteRepository;
    @Autowired
    public UtenteRepository utenteRepository;
    @Autowired
    public NutrizionistaRepository nutrizionistaRepository;

    @Autowired
    public SchedaAlimentareServiceImpl(UtenteRepository u,SchedaAlimentareRepository repo,ClienteRepository c,NutrizionistaRepository n,CiboRepository ci,ContenutoSchedaAlimentareRepository conRepo){
        this.repository=repo;
        this.clienteRepository=c;
        this.nutrizionistaRepository=n;
        this.ciboRepository=ci;
        this.repositoryContenutoScheda=conRepo;
        this.utenteRepository=u;
    }

    @Override
    public SchedaAlimentareViewDTO createSchedaAlimentare(SchedaAlimentareCreationDTO schedaDTO) throws SchedaAlimentareException {

        SchedaAlimentare scheda = schedaAlimentareCreateCostructor(schedaDTO);

        scheda = repository.save(scheda);
        getListContenuto(schedaDTO.getContenutoScheda(), scheda);

        return entityToDTO(scheda);
    }

    @Override
    public void removeSchedaAlimentare(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<SchedaAlimentareViewDTO> getAll() {
        List<SchedaAlimentareViewDTO> schedeDTO = new ArrayList<SchedaAlimentareViewDTO>();

        List<SchedaAlimentare> schedeEntity = repository.findAll();

        if (schedeEntity.isEmpty()) {
            throw new RuntimeException();
        }
        for (SchedaAlimentare s : schedeEntity) {
            schedeDTO.add(entityToDTO(s));
        }
        return schedeDTO;
    }

    @Override
    public SchedaAlimentareViewDTO getById(Integer id) {
        SchedaAlimentareViewDTO dto = new SchedaAlimentareViewDTO();

        Optional<SchedaAlimentare> entity = repository.findById(id);

        if (!entity.isPresent()) {
            throw new RuntimeException();
        } else {
            dto = entityToDTO(entity.get());
        }
        return dto;
    }

    @Override
    public List<SchedaAlimentareViewDTO> findByIdNutrizionista(Integer idNutrizionista) {
        List<SchedaAlimentareViewDTO> listDTO = new ArrayList<SchedaAlimentareViewDTO>();

        List<SchedaAlimentare> entity = repository.findByIdNutrizionista(idNutrizionista);

        if (entity.isEmpty()) {
            throw new RuntimeException();
        } else {
            for (SchedaAlimentare s : entity) {
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    @Override
    public List<SchedaAlimentareViewDTO> findByIdCliente(Integer idCliente) {
        List<SchedaAlimentareViewDTO> listDTO = new ArrayList<SchedaAlimentareViewDTO>();

        List<SchedaAlimentare> entity = repository.findByIdCliente(idCliente);

        if (entity.isEmpty()) {
            throw new RuntimeException();
        } else {
            for (SchedaAlimentare s : entity) {
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    @Override
    public List<SchedaAlimentareViewDTO> findByDataCreazione(Date data) {
        List<SchedaAlimentareViewDTO> listDTO = new ArrayList<SchedaAlimentareViewDTO>();

        List<SchedaAlimentare> entity = repository.findByDataCreazione(data);

        if (entity.isEmpty()) {
            throw new RuntimeException();
        } else {
            for (SchedaAlimentare s : entity) {
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    public SchedaAlimentare schedaAlimentareCreateCostructor(SchedaAlimentareCreationDTO dto) throws SchedaAlimentareException {
        SchedaAlimentare scheda = new SchedaAlimentare();

        Optional<Cliente>clOpt=clienteRepository.findById(dto.getIdCliente());
        if(clOpt.isPresent())
            scheda.setCliente(clOpt.get());
        else
            throw new SchedaAlimentareException("Cliente non trovato!");

        Optional<Nutrizionista>ntOpt=nutrizionistaRepository.findById(dto.getIdNutrizionista());
        if (ntOpt.isPresent())
            scheda.setNutrizionista(ntOpt.get());
        else
            throw new SchedaAlimentareException("Nutrizionista non trovato !");

        scheda.setDataCreazione(new Date());

        if(dto.getNote() == null)
            scheda.setNote(dto.getNote());
        else {
            if(dto.getNote().length() > 255 || dto.getNote().length() <4 )
                throw new SchedaAlimentareException("Note non valide!");
            else
                scheda.setNote(dto.getNote());
        }


        return scheda;
    }

    public SchedaAlimentareViewDTO entityToDTO(SchedaAlimentare entity) {
        SchedaAlimentareViewDTO dto = new SchedaAlimentareViewDTO();

        dto.setId(entity.getId());
        dto.setDataCreazione(entity.getDataCreazione());
        dto.setNote(entity.getNote());
        dto.setIdNutrizionista(entity.getNutrizionista().getId());

        dto.setClienteDTO(clienteEntityToDTO(entity.getCliente()));
        dto.setIdCliente(entity.getCliente().getId());

        return dto;
    }

    public ClienteViewDTO clienteEntityToDTO(Cliente cliente){
        ClienteViewDTO viewDTO = new ClienteViewDTO();

        Optional<Utente> utOpt = utenteRepository.findById(cliente.getUtente().getId());


        String nomeCognome=""+utOpt.get().getNome()+" "+utOpt.get().getCognome();

        viewDTO.setId(cliente.getId());
        viewDTO.setIdUtente(cliente.getUtente().getId());
        viewDTO.setPeso(cliente.getPeso());
        viewDTO.setAltezza(cliente.getAltezza());
        viewDTO.setNominativo(nomeCognome);

        if(cliente.getPrestNutrizionsita() == null)
            viewDTO.setPrestazioneNutrizionista(null);
        else
            viewDTO.setPrestazioneNutrizionista(cliente.getPrestNutrizionsita().getId());

        if(cliente.getPrestPersonalTrainer() == null)
            viewDTO.setPrestazionePersonalTrainer(null);
        else
            viewDTO.setPrestazionePersonalTrainer(cliente.getPrestPersonalTrainer().getId());

        return viewDTO;
    }

    private void getListContenuto(List<ContenutoSchedaAlimentareCreationDTO> dtos, SchedaAlimentare scheda) throws SchedaAlimentareException {

        for(int i=0;i<dtos.size();i++){

            //SALVO IL CONTENUTO PRINCIPALE
            ContenutoSchedaAlimentare entity= new ContenutoSchedaAlimentare();

            Boolean matchPrio=dtos.get(i).getPriorita().matches("^[A-Za-z]{8,11}$");
            if(dtos.get(i).getPriorita().length() < 8 || dtos.get(i).getPriorita().length() > 11 || !matchPrio)
                throw new SchedaAlimentareException("Priorità non valida");
            else
                entity.setPriorita(dtos.get(i).getPriorita());

            if(dtos.get(i).getQuantita() < 1)
                throw new SchedaAlimentareException("Quantità non valida");
            else
                entity.setQuantita(dtos.get(i).getQuantita());


            Boolean matchPasto=dtos.get(i).getPasto().matches("^[A-Za-z]{4,9}$");
            if(dtos.get(i).getPasto().length() <4 || dtos.get(i).getPasto().length() > 9 || !matchPasto)
                throw new SchedaAlimentareException("Pasto non valido");
            else
                entity.setPasto(dtos.get(i).getPasto());

            if(dtos.get(i).getGiorno() == null || dtos.get(i).getGiorno() <1 || dtos.get(i).getGiorno() > 7)
                throw new SchedaAlimentareException("Giorno non valido!");
            else
                entity.setGiorno(dtos.get(i).getGiorno());

            if(dtos.get(i).getCibo() == null)
                throw new SchedaAlimentareException("Cibo non valido!");
            else{
                Optional<Cibo> esOpt=ciboRepository.findById(dtos.get(i).getCibo());
                if(esOpt.isPresent())
                    entity.setCibo(esOpt.get());
                else
                    throw new SchedaAlimentareException("Cibo non valido!");
            }


            entity.setSchedaAlimentare(scheda);
            entity.setPrincipale(null);

            entity=repositoryContenutoScheda.save(entity);
            //DOPO AVER SALVATO QUELLO PRINCIPALE DEVO PRENDERMI IL SUO ID PER SETTARLO NEL CAMPO PRINCIPALE DELLE ALTERNATIVE
            Integer idPrincipaleDelleAlternative=entity.getId();
            if(dtos.get(i).getAlternative() != null){
                for(int j=0;j<dtos.get(i).getAlternative().size();j++){

                        //SALVO IL CONTENUTO DELLE ALTERNATIVE DEL PRIMO ELMENTO DELLA LISTA DI DTOS
                        ContenutoSchedaAlimentare entityAlt= new ContenutoSchedaAlimentare();

                        entityAlt.setPriorita(dtos.get(i).getAlternative().get(j).getPriorita());
                        entityAlt.setQuantita(dtos.get(i).getAlternative().get(j).getQuantita());
                        entityAlt.setGiorno(dtos.get(i).getAlternative().get(j).getGiorno());
                        entityAlt.setPasto(dtos.get(i).getAlternative().get(j).getPasto());

                        Optional<Cibo> esOpt2=ciboRepository.findById(dtos.get(i).getAlternative().get(j).getCibo());
                        if(esOpt2.isPresent()){
                            entityAlt.setCibo(esOpt2.get());
                        }

                        entityAlt.setSchedaAlimentare(scheda);
                        entityAlt.setPrincipale(idPrincipaleDelleAlternative);


                        entityAlt=repositoryContenutoScheda.save(entityAlt);
                }
            }

        }
    }
}