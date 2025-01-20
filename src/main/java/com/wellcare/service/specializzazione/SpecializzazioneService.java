package com.wellcare.service.specializzazione;

import com.wellcare.controller.dto.registration.ClienteCreationDTO;
import com.wellcare.controller.dto.registration.NutrizionistaCreationDTO;
import com.wellcare.controller.dto.registration.PersonalTrainerCreationDTO;
import com.wellcare.controller.dto.registration.RegistrationDTO;
import com.wellcare.model.*;
import com.wellcare.repository.abbonamento.AbbonamentoRepository;
import com.wellcare.repository.cliente.ClienteRepository;
import com.wellcare.repository.nutrizionista.NutrizionistaRepository;
import com.wellcare.repository.personalTrainer.PersonalTrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpecializzazioneService {

    @Autowired
    public AbbonamentoRepository abbonamentoRepository;

    @Autowired
    public PersonalTrainerRepository personalTrainerRepository;

    @Autowired
    public NutrizionistaRepository nutrizionistaRepository;

    @Autowired
    public ClienteRepository clienteRepository;

    public void specializzaUtente(RegistrationDTO dto, Utente utente){
        PersonalTrainerCreationDTO pTDTO=new PersonalTrainerCreationDTO();
        pTDTO=dto.getPersonalTrainer();

        NutrizionistaCreationDTO nDTO=new NutrizionistaCreationDTO();
        nDTO=dto.getNutrizionista();

        ClienteCreationDTO cDTO=new ClienteCreationDTO();
        cDTO=dto.getCliente();

        if(dto.getPersonalTrainer()!=null){
            PersonalTrainer pTEntity=new PersonalTrainer();

            Optional<Abbonamento> abOpt=abbonamentoRepository.findById(dto.getPersonalTrainer().getIdAbbonamento());
            if(abOpt.isPresent()){
                pTEntity.setAbbonamento(abOpt.get());
            }
            pTEntity.setIndirizzo(pTDTO.getIndirizzo());
            pTEntity.setDataUltimoPagamento(pTDTO.getDataUltimoPagamento());
            pTEntity.setUtente(utente);
            personalTrainerRepository.save(pTEntity);
            return;
        }

        if(dto.getNutrizionista()!=null){

            Nutrizionista nTEntity=new Nutrizionista();
            Optional<Abbonamento> abOpt=abbonamentoRepository.findById(dto.getNutrizionista().getIdAbbonamento());
            if(abOpt.isPresent()){
                nTEntity.setAbbonamento(abOpt.get());
            }
            nTEntity.setIndirizzo(nDTO.getIndirizzo());
            nTEntity.setDataUltimoPagamento(nDTO.getDataUltimoPagamento());
            nTEntity.setUtente(utente);
            nutrizionistaRepository.save(nTEntity);
            return;
        }


        if (dto.getCliente()!=null){
            Cliente cEntity=new Cliente();
            cEntity.setAltezza(cDTO.getAltezza());
            cEntity.setPeso(cDTO.getPeso());
            cEntity.setUtente(utente);
            clienteRepository.save(cEntity);
            return;
        }
    }

}
