package com.wellcare.controller;

import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaViewDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerViewDTO;
import com.wellcare.controller.dto.registration.ClienteCreationDTO;
import com.wellcare.controller.dto.registration.NutrizionistaCreationDTO;
import com.wellcare.controller.dto.registration.PersonalTrainerCreationDTO;
import com.wellcare.controller.dto.registration.RegistrationDTO;
import com.wellcare.controller.dto.utente.UtenteViewDTO;
import com.wellcare.exceptions.UtenteException;
import com.wellcare.service.cliente.ClienteService;
import com.wellcare.service.nutrizionista.NutrizionistaService;
import com.wellcare.service.personalTrainer.PersonalTrainerService;
import com.wellcare.service.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path ="public/registration")
public class RegistrationController {
    @Autowired
    public UtenteService service;
    @Autowired
    public ClienteService clienteService;
    @Autowired
    public PersonalTrainerService personalTrainerService;
    @Autowired
    public NutrizionistaService nutrizionistaService;

    @PostMapping(path="/create")
    public ResponseEntity<UtenteViewDTO> createUser(@RequestBody RegistrationDTO utente) throws UtenteException {
        UtenteViewDTO viewDTO=service.createUtente(utente);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }

    @PostMapping(path="/create/cliente")
    public ResponseEntity<ClienteViewDTO> createCustomer(@RequestBody ClienteCreationDTO cliente) {
        ClienteViewDTO viewDTO=clienteService.createCliente(cliente);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }

    @PostMapping(path="/create/personaltrainer")
    public ResponseEntity<PersonalTrainerViewDTO> createPersonaTrainer(@RequestBody PersonalTrainerCreationDTO pt) {
        PersonalTrainerViewDTO viewDTO=personalTrainerService.createPersonalTrainer(pt);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }

    @PostMapping(path="/create/nutrizionista")
    public ResponseEntity<NutrizionistaViewDTO> createNutritionist(@RequestBody NutrizionistaCreationDTO nt) {
        NutrizionistaViewDTO viewDTO=nutrizionistaService.createNutrizionista(nt);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
}