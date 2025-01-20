package com.wellcare.service.schedaAllenamento;

import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoCreationDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.controller.dto.schedaAllenamento.SchedaAllenamentoCreationDTO;
import com.wellcare.controller.dto.schedaAllenamento.SchedaAllenamentoViewDTO;
import com.wellcare.model.*;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.repository.contenutoSchedaAllenamento.ContenutoSchedaAllenamentoRepository;
import com.wellcare.repository.esercizio.EsercizioRepository;
import com.wellcare.repository.personalTrainer.PersonalTrainerRepository;
import com.wellcare.repository.schedaAllenamento.SchedaAllenamentoRepository;
import com.wellcare.exceptions.SchedaAllenamentoException;
import com.wellcare.repository.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class SchedaAllenamentoServiceImpl implements SchedaAllenamentoService {



    public SchedaAllenamentoRepository repository;
    @Autowired
    public ContenutoSchedaAllenamentoRepository repositoryContenutoScheda;
    @Autowired
    public PersonalTrainerRepository personalTrainerRepository;
    @Autowired
    public ClienteRepository clienteRepository;
    @Autowired
    public EsercizioRepository esercizioRepository;
    @Autowired
    public UtenteRepository utenteRepository;

    @Autowired
    public SchedaAllenamentoServiceImpl(SchedaAllenamentoRepository r){

        this.repository=r;
    }


    @Override
    public SchedaAllenamentoViewDTO createSchedaAllenamento(SchedaAllenamentoCreationDTO schedaDTO){
        SchedaAllenamento scheda= schedaAllenamentoCreateCostructor(schedaDTO);

        scheda=repository.save(scheda);

        getListContenuto(schedaDTO.getContenutoScheda(),scheda);

        return entityToDTO(scheda);
    }

    @Override
    public SuccessResponseDTO removeSchedaAllenamento(Integer id) throws SchedaAllenamentoException {
        Optional<SchedaAllenamento> scheda=repository.findById(id);


        if(scheda.get().getId() != null)
            repository.deleteById(id);
        else {
            if(scheda.get().getId() == null || id == null)
                throw new SchedaAllenamentoException("Scheda Allenamento non trovata!");
        }
        return new SuccessResponseDTO("Eliminazione effettuata con successo!");
    }

    @Override
    public List<SchedaAllenamentoViewDTO> getAll() throws SchedaAllenamentoException{
        List<SchedaAllenamentoViewDTO> schedeDTO= new ArrayList<SchedaAllenamentoViewDTO>();

        List<SchedaAllenamento> schedeEntity= repository.findAll();

        if(schedeEntity.isEmpty()){
            throw new SchedaAllenamentoException("Scheda Allenamento non trovata !");
        }
        for(SchedaAllenamento s : schedeEntity){
            schedeDTO.add(entityToDTO(s));
        }
        return schedeDTO;
    }

    @Override
    public SchedaAllenamentoViewDTO getById(Integer id) throws SchedaAllenamentoException{
        SchedaAllenamentoViewDTO dto= new SchedaAllenamentoViewDTO();

        Optional<SchedaAllenamento> entity = repository.findById(id);

        if(!entity.isPresent()){
            throw new SchedaAllenamentoException("Scheda Allenamento non trovata !");
        }
        else {
            dto=entityToDTO(entity.get());
        }
        return dto;
    }

    @Override
    public List<SchedaAllenamentoViewDTO> findByIdPersonalTrainer(Integer idPersonalTrainer)throws SchedaAllenamentoException{
        List<SchedaAllenamentoViewDTO> listDTO= new ArrayList<SchedaAllenamentoViewDTO>();

        List<SchedaAllenamento> entity = repository.findByIdPersonalTrainer(idPersonalTrainer);

        if(entity.isEmpty()){
            throw new SchedaAllenamentoException("Scheda Allenamento non trovata !");
        }
        else {
            for(SchedaAllenamento s : entity){
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    @Override
    public List<SchedaAllenamentoViewDTO> findByIdCliente(Integer idCliente) throws SchedaAllenamentoException{
        List<SchedaAllenamentoViewDTO> listDTO= new ArrayList<SchedaAllenamentoViewDTO>();

        List<SchedaAllenamento> entity = repository.findByIdCliente(idCliente);

        if(entity.isEmpty()){
            throw new SchedaAllenamentoException("Scheda Allenamento non trovata !");
        }
        else {
            for(SchedaAllenamento s : entity){
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }
    public List<SchedaAllenamentoViewDTO> findByDataCreazione(Date data) throws SchedaAllenamentoException{
        List<SchedaAllenamentoViewDTO> listDTO= new ArrayList<SchedaAllenamentoViewDTO>();

        List<SchedaAllenamento> entity = repository.findByDataCreazione(data);

        if(entity.isEmpty()){
            throw new SchedaAllenamentoException("Scheda Allenamento non trovata !");
        }
        else {
            for(SchedaAllenamento s : entity){
                listDTO.add(entityToDTO(s));
            }
        }
        return listDTO;
    }

    public String findNote(Integer id){
        String noteSchedaAllenamento;

        noteSchedaAllenamento=repository.findNoteById(id);

        return  noteSchedaAllenamento;
    }



    private SchedaAllenamento schedaAllenamentoCreateCostructor(SchedaAllenamentoCreationDTO dto){
        SchedaAllenamento scheda = new SchedaAllenamento();
        scheda.setNote(dto.getNote());
        scheda.setDataCreazione(new Date());

        Optional<Cliente> clOpt = clienteRepository.findById(dto.getIdCliente());
        if(clOpt.isPresent()){
            scheda.setCliente(clOpt.get());
        }

        Optional<PersonalTrainer> ptOpt = personalTrainerRepository.findById(dto.getIdPersonalTrainer());
        if(ptOpt.isPresent()){
            scheda.setPersonalTrainer(ptOpt.get());
        }

        return scheda;
    }
    private SchedaAllenamentoViewDTO entityToDTO(SchedaAllenamento entity){
        SchedaAllenamentoViewDTO dto= new SchedaAllenamentoViewDTO();

        dto.setId(entity.getId());
        dto.setDataCreazione(entity.getDataCreazione());
        dto.setNote(entity.getNote());
        dto.setIdCliente(entity.getCliente().getId());
        dto.setIdPersonalTrainer(entity.getPersonalTrainer().getId());
        dto.setClienteDTO(clienteEntityToDTO(entity.getCliente()));

        return dto;
    }
    private ClienteViewDTO clienteEntityToDTO(Cliente cliente){
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
    private void getListContenuto(List<ContenutoSchedaAllenamentoCreationDTO> dtos, SchedaAllenamento scheda){

        for(int i=0;i<dtos.size();i++){

            //SALVO IL CONTENUTO PRINCIPALE
            ContenutoSchedaAllenamento entity= new ContenutoSchedaAllenamento();
            entity.setPriorita(dtos.get(i).getPriorita());
            entity.setRipetizioni(dtos.get(i).getRipetizioni());
            entity.setGiorno(dtos.get(i).getGiorno());

            Optional<Esercizio> esOpt=esercizioRepository.findById(dtos.get(i).getEsercizio());
            if(esOpt.isPresent()){
                entity.setEsercizio(esOpt.get());
            }

            entity.setSchedaAllenamento(scheda);
            entity.setPrincipale(null);

            entity=repositoryContenutoScheda.save(entity);
            //DOPO AVER SALVATO QUELLO PRINCIPALE DEVO PRENDERMI IL SUO ID PER SETTARLO NEL CAMPO PRINCIPALE DELLE ALTERNATIVE
            Integer idPrincipaleDelleAlternative=entity.getId();
            if(dtos.get(i).getAlternative() != null){
                for(int j=0;j<dtos.get(i).getAlternative().size();j++){

                    //SALVO IL CONTENUTO DELLE ALTERNATIVE DEL PRIMO ELMENTO DELLA LISTA DI DTOS
                    ContenutoSchedaAllenamento entityAlt= new ContenutoSchedaAllenamento();

                    entityAlt.setPriorita(dtos.get(i).getAlternative().get(j).getPriorita());
                    entityAlt.setRipetizioni(dtos.get(i).getAlternative().get(j).getRipetizioni());
                    entityAlt.setGiorno(dtos.get(i).getAlternative().get(j).getGiorno());

                    Optional<Esercizio> esOpt2=esercizioRepository.findById(dtos.get(i).getAlternative().get(j).getEsercizio());
                    if(esOpt2.isPresent()){
                        entityAlt.setEsercizio(esOpt2.get());
                    }

                    entityAlt.setSchedaAllenamento(scheda);
                    entityAlt.setPrincipale(idPrincipaleDelleAlternative);

                    repositoryContenutoScheda.save(entityAlt);
                }
            }

        }
    }

}
