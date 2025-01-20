package com.wellcare.service.personalTrainer;


import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.personalTrainer.CustomerAdditionToPersonalTrainerDTO;
import com.wellcare.controller.dto.personalTrainer.CustomerRemovalToPersonalTrainerDTO;
import com.wellcare.controller.dto.registration.PersonalTrainerCreationDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerUpdateDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.model.*;
import com.wellcare.repository.abbonamento.AbbonamentoRepository;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.repository.personalTrainer.PersonalTrainerRepository;
import com.wellcare.exceptions.PersonalTrainerException;
import com.wellcare.repository.utente.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalTrainerServiceImpl implements PersonalTrainerService{

    @Autowired
    public PersonalTrainerRepository repository;
    @Autowired
    public AbbonamentoRepository abbonamentoRepository;
    @Autowired
    public ClienteRepository clienteRepository;
    @Autowired
    public UtenteRepository utenteRepository;

    @Override
    public PersonalTrainerViewDTO createPersonalTrainer(PersonalTrainerCreationDTO dto) {
        PersonalTrainer pt=createPersonalTrainerCostructor(dto);

        pt=repository.save(pt);

        return entityToDTO(pt);
    }

    private PersonalTrainer createPersonalTrainerCostructor(PersonalTrainerCreationDTO dto){
        PersonalTrainer pt=new PersonalTrainer();

        pt.setIndirizzo(dto.getIndirizzo());
        pt.setDataUltimoPagamento(dto.getDataUltimoPagamento());

        Optional<Abbonamento> abOpt=abbonamentoRepository.findById(dto.getIdAbbonamento());
        if(abOpt.isPresent()) {
            pt.setAbbonamento(abOpt.get());
        }

        Optional<Utente> usOpt = utenteRepository.findById(dto.getIdUtente());
        if(!usOpt.isPresent()){
            throw new RuntimeException("Utente non trovato");
        }
        pt.setUtente(usOpt.get());

        return pt;
    }


    @Override
    public PersonalTrainerViewDTO updatePersonalTrainer(PersonalTrainerUpdateDTO dto, Integer id) throws PersonalTrainerException {
        Optional<PersonalTrainer> ptOpt=repository.findById(id);

        if(ptOpt.isPresent()){
            ptOpt.get().setIndirizzo(dto.getIndirizzo());
            ptOpt.get().setDataUltimoPagamento(dto.getDataUltimoPagamento());

            Optional<Abbonamento> abbOpt=abbonamentoRepository.findById(dto.getIdAbbonamento());
            if (abbOpt.isPresent()){
                ptOpt.get().setAbbonamento(abbOpt.get());
            }
            repository.save(ptOpt.get());
        }
        else {
            throw new PersonalTrainerException("Nessun Personal-Trainer trovato!");
        }
        return entityToDTO(ptOpt.get());
    }

    @Override
    public PersonalTrainerViewDTO acceptCustomerRequest(CustomerAdditionToPersonalTrainerDTO dto, Integer id) throws PersonalTrainerException {
        Optional<PersonalTrainer> ptOpt=repository.findById(id);
        Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());

        //CONTROLLO SE IL PERSONAL TRAINER E IL CLIENTE SONO PRESENTI NEL DB
        if(ptOpt.isPresent() && clOpt.isPresent()){

            //CONTROLLO SE IL CLIENTE è GIà ASSOCIATO A UN PERSONAL TRAINER
            //SE è ASSOCIATO A UN PERSONAL TRAINER GENERO UN EXCEPTION
            if(clOpt.get().getPrestPersonalTrainer() == null){
                //AGGIUNGO IL L'ID CLIENTE ALLA LISTA DEI CLIENTI DEL PT
                addCustomer(dto,id);

                //ELIMINO L'ID DEL CLIENTE DALLA LISTA DI RICHIESTE DI PRESTAZIONE
                String listRichiesteClienti="";
                listRichiesteClienti=removeIdFromString(ptOpt.get().getListaIdClienti(),dto.getIdCliente());
                if(listRichiesteClienti.equals(""))
                    ptOpt.get().setListaRichiesteIdClienti(null);
                else
                    ptOpt.get().setListaRichiesteIdClienti(listRichiesteClienti);

                //AGGIORNO IL CAMPO "prest_pt" NEL CLIENTE
                clOpt.get().setPrestPersonalTrainer(ptOpt.get());

                //SALVO LE MODIFICHE
                repository.save(ptOpt.get());
                clienteRepository.save(clOpt.get());
            }
            else {
                throw new PersonalTrainerException("Cliente già associato a un altro Personal Trainer!");
            }

        }
        else {
            throw new PersonalTrainerException("PersonalTrainer o Cliente non trovati!");
        }

        return entityToDTO(ptOpt.get());
    }

    public PersonalTrainerViewDTO addCustomer(CustomerAdditionToPersonalTrainerDTO dto, Integer id) throws PersonalTrainerException {
        Optional<PersonalTrainer> ptOpt=repository.findById(id);
        Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());

        //CONTROLLO SE IL PERSONAL TRAINER E IL CLIENTE SONO PRESENTI NEL DB
        if(ptOpt.isPresent() && clOpt.isPresent()){
            String listaClientiOpt="";
            if(ptOpt.get().getListaIdClienti() == null)
                listaClientiOpt+=dto.getIdCliente();
            else {
                listaClientiOpt = ptOpt.get().getListaIdClienti();
                listaClientiOpt += ","+dto.getIdCliente();
            }
            ptOpt.get().setListaIdClienti(listaClientiOpt);

            //AGGIORNO IL CAMPO "prest_pt" NEL CLIENTE
            clOpt.get().setPrestPersonalTrainer(ptOpt.get());

            //SALVO LE MODIFICHE
            repository.save(ptOpt.get());
            clienteRepository.save(clOpt.get());
        }
        else {
            throw new PersonalTrainerException("PersonalTrainer o Cliente non trovati!");
        }

        return entityToDTO(ptOpt.get());
    }

    @Override
    public SuccessResponseDTO rejectCustomerRequest(CustomerRemovalToPersonalTrainerDTO dto, Integer id) throws PersonalTrainerException {
        Optional<PersonalTrainer> ptOpt=repository.findById(id);
        Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());

        //CONTROLLO SE IL PERSONAL TRAINER E IL CLIENTE SONO PRESENTI NEL DB
        if(ptOpt.isPresent() && clOpt.isPresent()){
            //SETTIAMO LA NUOVA LISTA CLIENTI DEL PERSONAL TRAINER
            String listRichiesteClienti=removeIdFromString(ptOpt.get().getListaRichiesteIdClienti(),dto.getIdCliente());
            if(listRichiesteClienti.equals(""))
                ptOpt.get().setListaRichiesteIdClienti(null);
            else
                ptOpt.get().setListaRichiesteIdClienti(listRichiesteClienti);


            //SALVO LE MODIFICHE
            repository.save(ptOpt.get());
        }
        else {
            throw new PersonalTrainerException("PersonalTrainer o Cliente non trovati!");
        }
        return new SuccessResponseDTO("Operazione riuscita con successo!");
    }

    @Override
    public PersonalTrainerViewDTO removeCustomer(CustomerRemovalToPersonalTrainerDTO dto, Integer id) throws PersonalTrainerException {
        Optional<PersonalTrainer> ptOpt=repository.findById(id);
        Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());

        //CONTROLLO SE IL PERSONAL TRAINER E IL CLIENTE SONO PRESENTI NEL DB
        if(ptOpt.isPresent() && clOpt.isPresent()){
            //SETTIAMO LA NUOVA LISTA CLIENTI DEL PERSONAL TRAINER
            String risulatoRimozione=removeIdFromString(ptOpt.get().getListaIdClienti(),dto.getIdCliente());
            if(risulatoRimozione.equals(""))
                ptOpt.get().setListaIdClienti(null);
            else
                ptOpt.get().setListaIdClienti(risulatoRimozione);

            //SETTIAMO A "null" IL VALORE "prest_pt" IN CLIENTE
            clOpt.get().setPrestPersonalTrainer(null);

            //SALVO LE MODIFICHE
            repository.save(ptOpt.get());
            clienteRepository.save(clOpt.get());
        }
        else {
            throw new PersonalTrainerException("PersonalTrainer o Cliente non trovati!");
        }

        return entityToDTO(ptOpt.get());
    }



    @Override
    public List<PersonalTrainerViewDTO> findAll() throws PersonalTrainerException {
        List<PersonalTrainerViewDTO> listDto=new ArrayList<PersonalTrainerViewDTO>();

        List<PersonalTrainer> listEntity=repository.findAll();

        if(listEntity.isEmpty()){
            throw new PersonalTrainerException("Nessun Personal-Trainer trovato!");
        }
        for (PersonalTrainer p : listEntity){
            listDto.add(entityToDTO(p));
        }
        return listDto;
    }

    @Override
    public PersonalTrainerViewDTO findById(Integer id) throws PersonalTrainerException{
        PersonalTrainerViewDTO dto=new PersonalTrainerViewDTO();

        Optional<PersonalTrainer> entity=repository.findById(id);

        if(!entity.isPresent()){
            throw new PersonalTrainerException("Nessun Personal-Trainer trovato!");
        }
        else {
            dto=entityToDTO(entity.get());
        }
        return dto;
    }

    @Override
    public PersonalTrainerViewDTO findByIdUtente(Integer id) throws PersonalTrainerException {
        PersonalTrainerViewDTO dto=new PersonalTrainerViewDTO();

        Optional<PersonalTrainer> entity=repository.findByIdUtente(id);

        if(!entity.isPresent()){
            throw new PersonalTrainerException("Nessun Personal-Trainer trovato!");
        }
        else {
            dto=entityToDTO(entity.get());
        }
        return dto;
    }
    private List<ClienteViewDTO> getListClienteViewDTO(String listClienti){
        List<ClienteViewDTO> listClientView=new ArrayList<>();

        String lista=listClienti;

        for(int i=0;i<lista.length();i++){

            char c=lista.charAt(i);
            int idCliente=Character.getNumericValue(c);

            Optional<Cliente> clOpt=clienteRepository.findById(idCliente);
            if(clOpt.isPresent()){
                listClientView.add(entityClienteToDTO(clOpt.get()));
            }
            i++;
        }
        return listClientView;
    }
    private ClienteViewDTO entityClienteToDTO(Cliente cliente){
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
    private PersonalTrainerViewDTO entityToDTO(PersonalTrainer personalTrainer){
        PersonalTrainerViewDTO viewDTO=new PersonalTrainerViewDTO();
        List<ClienteViewDTO> listClienti=new ArrayList<>();
        List<ClienteViewDTO> listRichiestaClienti=new ArrayList<>();

        viewDTO.setId(personalTrainer.getId());
        viewDTO.setIdUtente(personalTrainer.getUtente().getId());
        viewDTO.setIndirizzo(personalTrainer.getIndirizzo());
        viewDTO.setIdAbbonamento(personalTrainer.getAbbonamento().getId());
        viewDTO.setDataUltimoPagamento(personalTrainer.getDataUltimoPagamento());

        if(personalTrainer.getListaIdClienti() == null){
            viewDTO.setListaClienti(listClienti);
        }
        else {
            listClienti=getListClienteViewDTO(personalTrainer.getListaIdClienti());
            viewDTO.setListaClienti(listClienti);
        }

        if(personalTrainer.getListaRichiesteIdClienti() == null){
            viewDTO.setListaRichiestaClienti(listRichiestaClienti);
        }
        else {
            listRichiestaClienti=getListClienteViewDTO(personalTrainer.getListaRichiesteIdClienti());
            viewDTO.setListaRichiestaClienti(listRichiestaClienti);
        }

        return viewDTO;
    }
    private String removeIdFromString(String listaClienti,Integer idDaTogliere){
        String lista=listaClienti;
        StringBuilder stringBuilder=new StringBuilder(lista);

        int id=idDaTogliere.intValue();
        char c= (char) id;
        Integer lunghezzaStringa=lista.length();

        if(lista.indexOf(idDaTogliere.toString()) != -1){
            int indiceCarattere=lista.indexOf(idDaTogliere.toString());

            if(lunghezzaStringa == 1)
                stringBuilder.deleteCharAt(indiceCarattere);
            else {
                if(lunghezzaStringa == indiceCarattere+1){
                    stringBuilder.deleteCharAt(indiceCarattere);
                    stringBuilder.deleteCharAt(indiceCarattere-1);
                }
                else {
                    if(lunghezzaStringa >= indiceCarattere){
                        stringBuilder.deleteCharAt(indiceCarattere);
                        stringBuilder.deleteCharAt(indiceCarattere);
                    }
                }
            }
            lista=stringBuilder.toString();
            return lista;
        }

        return lista;
    }
}
