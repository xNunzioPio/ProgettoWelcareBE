package com.wellcare.service.cliente;

import com.wellcare.controller.dto.cliente.RichiestaPrestazioneNutrizionistaDTO;
import com.wellcare.controller.dto.cliente.RichiestaPrestazionePersonalTrainerDTO;
import com.wellcare.controller.dto.collaborazioni.CollaborazioniViewDTO;
import com.wellcare.controller.dto.registration.ClienteCreationDTO;
import com.wellcare.controller.dto.cliente.ClienteUpdateDTO;
import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.registration.RegistrationDTO;
import com.wellcare.controller.dto.utente.UtenteViewDTO;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.exceptions.PersonalTrainerException;
import com.wellcare.model.Cliente;
import com.wellcare.model.Nutrizionista;
import com.wellcare.model.PersonalTrainer;
import com.wellcare.model.Utente;
import com.wellcare.repository.abbonamento.AbbonamentoRepository;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.exceptions.ClienteException;
import com.wellcare.repository.nutrizionista.NutrizionistaRepository;
import com.wellcare.repository.personalTrainer.PersonalTrainerRepository;
import com.wellcare.repository.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ClienteServiceImpl implements ClienteService{

    @Autowired
    public ClienteRepository repository;

    @Autowired
    public UtenteRepository utenteRepository;

    @Autowired
    public AbbonamentoRepository abbonamentoRepository;
    @Autowired
    public NutrizionistaRepository nutrizionistaRepository;
    @Autowired
    public PersonalTrainerRepository personalTrainerRepository;

    @Override
    public ClienteViewDTO createCliente(ClienteCreationDTO dto) {
        Cliente cliente=createClienteCostructor(dto);

        cliente=repository.save(cliente);

        return entityToDTO(cliente);
    }

    private Cliente createClienteCostructor(ClienteCreationDTO dto){
        Cliente cliente=new Cliente();

        cliente.setAltezza(dto.getAltezza());
        cliente.setPeso(dto.getPeso());




        Optional<Utente> usOpt = utenteRepository.findById(dto.getIdUtente());
        if(!usOpt.isPresent()){
            throw new RuntimeException("Utente non trovato");
        }
        cliente.setUtente(usOpt.get());

        return cliente;
    }

    @Override
    public ClienteViewDTO updateCliente(ClienteUpdateDTO dto, Integer id) throws ClienteException {
        Optional<Cliente> clOpt=repository.findById(id);

        if(clOpt.isPresent()){
            clOpt.get().setPeso(dto.getPeso());
            clOpt.get().setAltezza(dto.getAltezza());


            if(dto.getPrestazioneNutrizionista() == null)
                clOpt.get().setPrestNutrizionsita(null);
            else {
                Optional<Nutrizionista> optNut=nutrizionistaRepository.findById(dto.getPrestazioneNutrizionista());
                clOpt.get().setPrestNutrizionsita(optNut.get());
            }


            if(dto.getPrestazionePersonalTrainer() == null)
                clOpt.get().setPrestPersonalTrainer(null);
            else {
                Optional<PersonalTrainer> optPt=personalTrainerRepository.findById(dto.getPrestazionePersonalTrainer());
                clOpt.get().setPrestPersonalTrainer(optPt.get());
            }


            repository.save(clOpt.get());
        }
        else {
            throw new ClienteException("Cliente non trovato!");
        }
        return entityToDTO(clOpt.get());
    }

    @Override
    public List<ClienteViewDTO> findAll() throws ClienteException {
        List<ClienteViewDTO> listViewDto=new ArrayList<ClienteViewDTO>();

        List<Cliente> listEntity=repository.findAll();
        if(listEntity.isEmpty()){
            throw new ClienteException("Nessun cliente trovato!");
        }
        for(Cliente c : listEntity){
            listViewDto.add(entityToDTO(c));
        }
        return listViewDto;
    }

    @Override
    public ClienteViewDTO findById(Integer id) throws ClienteException{
        ClienteViewDTO viewDTO=new ClienteViewDTO();

        Optional<Cliente> cliente=repository.findById(id);

        if (!cliente.isPresent()){
            throw new ClienteException("Nessun cliente trovato!");
        }
        else {
            viewDTO=entityToDTO(cliente.get());
        }
        return viewDTO;
    }

    @Override
    public ClienteViewDTO findByIdUtente(Integer id) throws ClienteException {
        ClienteViewDTO viewDTO=new ClienteViewDTO();

        Optional<Cliente> optCl=repository.findByIdUtente(id);

        if(optCl.isPresent()){
            viewDTO=entityToDTO(optCl.get());
        }
        else {
            throw new ClienteException("Cliente non trovato!");
        }
        return viewDTO;
    }
    public CollaborazioniViewDTO getCollaborationsByIdCliente(Integer idCliente) throws ClienteException{
        CollaborazioniViewDTO viewDTO=new CollaborazioniViewDTO();
        //CERCO IL CLIENTE
        Optional<Cliente> clOpt=repository.findById(idCliente);

        if(clOpt.isPresent()){
            viewDTO.setIdCliente(idCliente);
            //SETTO IL NOME E COGNOME DEL CLIENTE IN BASE ALL'ID UTENTE
            Optional<Utente> utenteClienteOpt=utenteRepository.findById(clOpt.get().getUtente().getId());
            String nominativoCliente=utenteClienteOpt.get().getNome()+" "+utenteClienteOpt.get().getCognome();
            viewDTO.setNominativoCliente(nominativoCliente);


            //SETTO IL NOME E COGNOME DEL NUTRIZIONISTA SE è PRESENTE
            if(clOpt.get().getPrestNutrizionsita() != null){
                Optional<Nutrizionista> nutOpt=nutrizionistaRepository.findById(clOpt.get().getPrestNutrizionsita().getId());
                Optional<Utente> utenteNutOpt=utenteRepository.findById(nutOpt.get().getUtente().getId());
                String nominativoNutr=utenteNutOpt.get().getNome()+" "+utenteNutOpt.get().getCognome();
                viewDTO.setIdNutrizionista(nutOpt.get().getId());
                viewDTO.setNominativoNutrizionista(nominativoNutr);
            }
            else {
                viewDTO.setNominativoNutrizionista(null);
                viewDTO.setIdNutrizionista(null);
            }

            //SETTO IL NOME E COGNOME DEL PERSONALTRAINER SE è PRESENTE
            if(clOpt.get().getPrestPersonalTrainer() != null){
                Optional<PersonalTrainer> ptOpt=personalTrainerRepository.findById(clOpt.get().getPrestPersonalTrainer().getId());
                Optional<Utente> utentePtOpt=utenteRepository.findById(ptOpt.get().getUtente().getId());
                String nominativoPersonalTrainer=utentePtOpt.get().getNome()+" "+utentePtOpt.get().getCognome();
                viewDTO.setNominativoPersonalTrainer(nominativoPersonalTrainer);
                viewDTO.setIdPersonalTrainer(ptOpt.get().getId());
            }
            else {
                viewDTO.setIdPersonalTrainer(null);
                viewDTO.setNominativoPersonalTrainer(null);
            }
        }
        else {
            throw new ClienteException("Cliente non trovato!");
        }



        return viewDTO;
    }

    @Override
    public void richiestaPrestazionePT(RichiestaPrestazionePersonalTrainerDTO dto,Integer idCliente) throws PersonalTrainerException {
        Optional<PersonalTrainer> ptOpt=personalTrainerRepository.findById(dto.getIdPersonalTrainer());
        Optional<Cliente> clOpt=repository.findById(idCliente);

        //CONTROLLO SE IL PERSONAL TRAINER E IL CLIENTE SONO PRESENTI NEL DB
        if(ptOpt.isPresent() && clOpt.isPresent()){
            String listaRichiesteClientiOpt="";
            if(ptOpt.get().getListaRichiesteIdClienti() == null)
                listaRichiesteClientiOpt+=idCliente;
            else {
                listaRichiesteClientiOpt = ptOpt.get().getListaRichiesteIdClienti();
                listaRichiesteClientiOpt += ","+idCliente;
            }
            ptOpt.get().setListaRichiesteIdClienti(listaRichiesteClientiOpt);

            //SALVO LE MODIFICHE
            personalTrainerRepository.save(ptOpt.get());
        }
        else {
            throw new PersonalTrainerException("PersonalTrainer o Cliente non trovati!");
        }
    }

    @Override
    public void richiestaPrestazioneNutr(RichiestaPrestazioneNutrizionistaDTO dto, Integer idCliente ) throws NutrizionistaException {
        Optional<Nutrizionista> nutOpt=nutrizionistaRepository.findById(dto.getIdNutrizionista());
        Optional<Cliente> clOpt=repository.findById(idCliente);

        //CONTROLLO SE IL NUTRIZIONISTA E IL CLIENTE SONO PRESENTI NEL DB
        if(nutOpt.isPresent() && clOpt.isPresent()){
            String listaRichiesteClientiOpt="";
            if(nutOpt.get().getListaRichiesteIdClienti() == null)
                listaRichiesteClientiOpt+=idCliente;
            else {
                listaRichiesteClientiOpt = nutOpt.get().getListaRichiesteIdClienti();
                listaRichiesteClientiOpt += ","+idCliente;
            }
            nutOpt.get().setListaRichiesteIdClienti(listaRichiesteClientiOpt);

            //SALVO LE MODIFICHE
            nutrizionistaRepository.save(nutOpt.get());
        }
        else {
            throw new NutrizionistaException("Nutrizionista o Cliente non trovati!");
        }
    }


    private ClienteViewDTO entityToDTO(Cliente cliente){
        ClienteViewDTO viewDTO=new ClienteViewDTO();
        Optional<Utente> utOpt=utenteRepository.findById(cliente.getUtente().getId());
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

}
