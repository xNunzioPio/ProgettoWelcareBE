package com.wellcare.service.nutrizionista;

import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.nutrizionista.CustomerAdditionToNutritionistDTO;
import com.wellcare.controller.dto.nutrizionista.CustomerRemovalToNutritionistDTO;
import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.registration.ClienteCreationDTO;
import com.wellcare.controller.dto.registration.NutrizionistaCreationDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaUpdateDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.exceptions.PersonalTrainerException;
import com.wellcare.model.Abbonamento;
import com.wellcare.model.Cliente;
import com.wellcare.model.Nutrizionista;
import com.wellcare.model.Utente;
import com.wellcare.repository.abbonamento.AbbonamentoRepository;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.repository.nutrizionista.NutrizionistaRepository;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.repository.utente.UtenteRepository;
import com.wellcare.service.cliente.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NutrizionistaServiceImpl implements NutrizionistaService {

    @Autowired
    public NutrizionistaRepository repository;
    @Autowired
    public ClienteRepository clienteRepository;
    @Autowired
    public AbbonamentoRepository abbonamentoRepository;
    @Autowired
    public UtenteRepository utenteRepository;

    @Override
    public NutrizionistaViewDTO createNutrizionista(NutrizionistaCreationDTO dto) {
        Nutrizionista nt=createNutrizionistaCostructor(dto);

        nt=repository.save(nt);

        return entityToDTO(nt);
    }

    private Nutrizionista createNutrizionistaCostructor(NutrizionistaCreationDTO dto){
        Nutrizionista nt=new Nutrizionista();

        nt.setIndirizzo(dto.getIndirizzo());
        nt.setDataUltimoPagamento(dto.getDataUltimoPagamento());

        Optional<Abbonamento> abOpt=abbonamentoRepository.findById(dto.getIdAbbonamento());
        if(abOpt.isPresent()) {
            nt.setAbbonamento(abOpt.get());
        }

        Optional<Utente> usOpt = utenteRepository.findById(dto.getIdUtente());
        if(!usOpt.isPresent()){
            throw new RuntimeException("Utente non trovato");
        }
        nt.setUtente(usOpt.get());

        return nt;
    }


    @Override
    public NutrizionistaViewDTO updateNutrizionista(NutrizionistaUpdateDTO dto, Integer id) throws NutrizionistaException{
        Optional<Nutrizionista> nutOpt=repository.findById(id);

        if(nutOpt.isPresent()){
            nutOpt.get().setIndirizzo(dto.getIndirizzo());
            nutOpt.get().setDataUltimoPagamento(dto.getDataUltimoPagamento());
            Optional<Abbonamento> abbOpt=abbonamentoRepository.findById(dto.getIdAbbonamento());
            if (abbOpt.isPresent()){
                nutOpt.get().setAbbonamento(abbOpt.get());
            }
            repository.save(nutOpt.get());
        }
        else {
            throw new NutrizionistaException("Nessun Nutrizionista trovato!");
        }
        return entityToDTO(nutOpt.get());
    }

    @Override
    public NutrizionistaViewDTO acceptCustomerRequest(CustomerAdditionToNutritionistDTO dto, Integer id) throws NutrizionistaException {
        Optional<Nutrizionista> nutOpt=repository.findById(id);
        Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());

        //CONTROLLO SE IL NUTRIZIONISTA E IL CLIENTE SONO PRESENTI NEL DB
        if(nutOpt.isPresent() && clOpt.isPresent()){

            //CONTROLLO SE IL CLIENTE è GIà ASSOCIATO A UN NUTRIZIONISTA
            //SE è ASSOCIATO A UN NUTRIZIONISTA GENERO UN EXCEPTION
            if(clOpt.get().getPrestNutrizionsita() == null){
                //AGGIUNGO IL L'ID CLIENTE ALLA LISTA DEI CLIENTI DEL NUTRIZIONISTA
                addCustomer(dto,id);

                //ELIMINO L'ID DEL CLIENTE DALLA LISTA DI RICHIESTE DI PRESTAZIONE
                String listRichiesteClienti="";
                listRichiesteClienti=removeIdFromString(nutOpt.get().getListaRichiesteIdClienti(),dto.getIdCliente());
                if(listRichiesteClienti.equals(""))
                    nutOpt.get().setListaRichiesteIdClienti(null);
                else
                    nutOpt.get().setListaRichiesteIdClienti(listRichiesteClienti);

                //SALVO LE MODIFICHE
                repository.save(nutOpt.get());
            }
            else {
                throw new NutrizionistaException("Cliente già associato a un altro Nutrizionista!");
            }
        }
        else {
            throw new NutrizionistaException("Nutrizionista o Cliente non trovati!");
        }

        return entityToDTO(nutOpt.get());
    }

    @Override
    public SuccessResponseDTO rejectCustomerRequest(CustomerRemovalToNutritionistDTO dto, Integer id) throws NutrizionistaException {
        Optional<Nutrizionista> nutOpt=repository.findById(id);
        Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());

        //CONTROLLO SE IL NUTRIZIONISTA E IL CLIENTE SONO PRESENTI NEL DB
        if(nutOpt.isPresent() && clOpt.isPresent()){
            //SETTIAMO LA NUOVA LISTA CLIENTI DEL NUTRIZIONISTA
            String listRichiesteClienti=removeIdFromString(nutOpt.get().getListaRichiesteIdClienti(),dto.getIdCliente());
            if(listRichiesteClienti.equals(""))
                nutOpt.get().setListaRichiesteIdClienti(null);
            else
                nutOpt.get().setListaRichiesteIdClienti(listRichiesteClienti);


            //SALVO LE MODIFICHE
            repository.save(nutOpt.get());
        }
        else {
            throw new NutrizionistaException("PersonalTrainer o Cliente non trovati!");
        }
        return new SuccessResponseDTO("Operazione riuscita con successo!");
    }


    public NutrizionistaViewDTO addCustomer(CustomerAdditionToNutritionistDTO dto, Integer id) throws NutrizionistaException {
        Optional<Nutrizionista> nutOpt=repository.findById(id);
        Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());

        //CONTROLLO SE IL NUTRIZIONISTA E IL CLIENTE SONO PRESENTI NEL DB
        if(nutOpt.isPresent() && clOpt.isPresent()){
            String listaClientiOpt="";
            if(nutOpt.get().getListaIdClienti() == null)
                listaClientiOpt+=dto.getIdCliente();
            else {
                listaClientiOpt = nutOpt.get().getListaIdClienti();
                listaClientiOpt += ","+dto.getIdCliente();
            }
            nutOpt.get().setListaIdClienti(listaClientiOpt);

            //AGGIORNO IL CAMPO "prest_nt" NEL CLIENTE
            clOpt.get().setPrestNutrizionsita(nutOpt.get());

            //SALVO LE MODIFICHE
            repository.save(nutOpt.get());
            clienteRepository.save(clOpt.get());
        }
        else {
            throw new NutrizionistaException("Nutrizionista o Cliente non trovati!");
        }

        return entityToDTO(nutOpt.get());
    }

    @Override
    public NutrizionistaViewDTO removeCustomer(CustomerRemovalToNutritionistDTO dto, Integer id) throws NutrizionistaException {
        Optional<Nutrizionista> nutOpt=repository.findById(id);
        Optional<Cliente> clOpt=clienteRepository.findById(dto.getIdCliente());

        //CONTROLLO SE IL NUTRIZIONISTA E IL CLIENTE SONO PRESENTI NEL DB
        if(nutOpt.isPresent() && clOpt.isPresent()){
            //SETTIAMO LA NUOVA LISTA CLIENTI DEL NUTRIZIONISTA
            String risulatoRimozione=removeIdFromString(nutOpt.get().getListaIdClienti(),dto.getIdCliente());
            if(risulatoRimozione.equals(""))
                nutOpt.get().setListaIdClienti(null);
            else
                nutOpt.get().setListaIdClienti(risulatoRimozione);

            //SETTIAMO A "null" IL VALORE "prest_nt" IN CLIENTE
            clOpt.get().setPrestNutrizionsita(null);

            //SALVO LE MODIFICHE
            repository.save(nutOpt.get());
            clienteRepository.save(clOpt.get());
        }
        else {
            throw new NutrizionistaException("Nutrizionista o Cliente non trovati!");
        }

        return entityToDTO(nutOpt.get());
    }

    @Override
    public List<NutrizionistaViewDTO> findAll() throws NutrizionistaException {
        List<NutrizionistaViewDTO> listViewDto=new ArrayList<NutrizionistaViewDTO>();

        List<Nutrizionista> listEntity=repository.findAll();

        if(listEntity.isEmpty()){
            throw new NutrizionistaException("Nessun Nutrizionista trovato!");
        }
        for (Nutrizionista n : listEntity){
            listViewDto.add(entityToDTO(n));
        }
        return listViewDto;
    }

    @Override
    public NutrizionistaViewDTO findById(Integer id) throws NutrizionistaException{
        NutrizionistaViewDTO nutrizionistaViewDTO=new NutrizionistaViewDTO();

        Optional<Nutrizionista> entity=repository.findById(id);

        if(!entity.isPresent()){
            throw new NutrizionistaException("Nessun Nutrizionista trovato!");
        }
        else {
            nutrizionistaViewDTO=entityToDTO(entity.get());
        }
        return nutrizionistaViewDTO;
    }

    @Override
    public NutrizionistaViewDTO findByIdUtente(Integer id) throws NutrizionistaException {
        NutrizionistaViewDTO nutrizionistaViewDTO=new NutrizionistaViewDTO();

        Optional<Nutrizionista> entity=repository.findByIdUtente(id);

        if(!entity.isPresent()){
            throw new NutrizionistaException("Nessun Nutrizionista trovato!");
        }
        else {
            nutrizionistaViewDTO=entityToDTO(entity.get());
        }
        return nutrizionistaViewDTO;
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

    private NutrizionistaViewDTO entityToDTO(Nutrizionista nutrizionista){
        NutrizionistaViewDTO viewDTO=new NutrizionistaViewDTO();
        List<ClienteViewDTO> listClienti=new ArrayList<>();
        List<ClienteViewDTO> listRichiesteClienti=new ArrayList<>();

        viewDTO.setId(nutrizionista.getId());
        viewDTO.setIdUtente(nutrizionista.getUtente().getId());
        viewDTO.setIndirizzo(nutrizionista.getIndirizzo());
        viewDTO.setIdAbbonamento(nutrizionista.getAbbonamento().getId());
        viewDTO.setDataUltimoPagamento(nutrizionista.getDataUltimoPagamento());

        if(nutrizionista.getListaIdClienti() == null){
            viewDTO.setListaClienti(listClienti);
            return viewDTO;
        }
        else {
            listClienti=getListClienteViewDTO(nutrizionista.getListaIdClienti());
            viewDTO.setListaClienti(listClienti);
        }

        if(nutrizionista.getListaRichiesteIdClienti() == null)
            viewDTO.setListaRichiestaClienti(listRichiesteClienti);
        else {
            listRichiesteClienti=getListClienteViewDTO(nutrizionista.getListaRichiesteIdClienti());
            viewDTO.setListaRichiestaClienti(listRichiesteClienti);
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
