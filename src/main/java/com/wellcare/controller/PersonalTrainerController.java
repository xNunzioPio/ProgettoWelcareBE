package com.wellcare.controller;


import com.wellcare.controller.dto.collaborazioni.CollaborazioniViewDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroViewDTO;
import com.wellcare.controller.dto.nutrizionista.CustomerAdditionToNutritionistDTO;
import com.wellcare.controller.dto.nutrizionista.CustomerRemovalToNutritionistDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaViewDTO;
import com.wellcare.controller.dto.personalTrainer.CustomerAdditionToPersonalTrainerDTO;
import com.wellcare.controller.dto.personalTrainer.CustomerRemovalToPersonalTrainerDTO;
import com.wellcare.controller.dto.registration.PersonalTrainerCreationDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerUpdateDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.exceptions.ClienteException;
import com.wellcare.exceptions.EserciziGiornalieriException;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.exceptions.PersonalTrainerException;
import com.wellcare.service.cliente.ClienteService;
import com.wellcare.service.eserciziGiornalieri.EserciziGiornalieriService;
import com.wellcare.service.personalTrainer.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
@Controller
@RequestMapping(path ="private/personalTrainer")
public class PersonalTrainerController {

    @Autowired
    public PersonalTrainerService service;
    @Autowired
    public EserciziGiornalieriService eserciziGiornalieriService;
    @Autowired
    public ClienteService clienteService;

    @GetMapping(path="/all")
    public ResponseEntity<List<PersonalTrainerViewDTO>> getAllPersonalTrainer() throws PersonalTrainerException {
        List<PersonalTrainerViewDTO> ListDto = service.findAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }
    @GetMapping(path="/{id}")
    public ResponseEntity<PersonalTrainerViewDTO> getSpecificPersonalTrainer(@PathVariable(required = true, name = "id") int id) throws PersonalTrainerException {
        PersonalTrainerViewDTO viewDto = service.findById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @GetMapping(path="idUtente/{id}")
    public ResponseEntity<PersonalTrainerViewDTO> getSpecificPersonalTrainerByIdUser(@PathVariable(required = true, name = "id") int id) throws PersonalTrainerException {
        PersonalTrainerViewDTO viewDto = service.findByIdUtente(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @PostMapping(path="/update/{id}")
    public ResponseEntity<PersonalTrainerViewDTO> updatePersonalTrainer(@RequestBody PersonalTrainerUpdateDTO dto, @PathVariable(required = true, name = "id") int id) throws PersonalTrainerException {
        PersonalTrainerViewDTO viewDTO=service.updatePersonalTrainer(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @PostMapping(path="/addCustomer/{id}")
    public ResponseEntity<PersonalTrainerViewDTO> addCustomer(@RequestBody CustomerAdditionToPersonalTrainerDTO dto, @PathVariable(required = true, name = "id") int id) throws PersonalTrainerException{
        PersonalTrainerViewDTO viewDTO=service.acceptCustomerRequest(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @PostMapping(path="/removeCustomer/{id}")
    public ResponseEntity<PersonalTrainerViewDTO> removeCustomer(@RequestBody CustomerRemovalToPersonalTrainerDTO dto, @PathVariable(required = true, name = "id") int id) throws PersonalTrainerException {
        PersonalTrainerViewDTO viewDTO=service.removeCustomer(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @PostMapping(path="/rejectCustomerRequest/{id}")
    public ResponseEntity<SuccessResponseDTO> rejectCustomerRequest(@RequestBody CustomerRemovalToPersonalTrainerDTO dto, @PathVariable(required = true, name = "id") int id) throws PersonalTrainerException {
        SuccessResponseDTO viewDTO=service.rejectCustomerRequest(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @GetMapping(path="/collaborazioni/idCliente/{id}")
    public ResponseEntity<CollaborazioniViewDTO> getCollaborationsByIdCliente(@PathVariable(required = true, name = "id") int id) throws  ClienteException {
        CollaborazioniViewDTO viewDto = clienteService.getCollaborationsByIdCliente(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @GetMapping(path="/monitoraggio/idSchedaAllenamento/{id}")
    public ResponseEntity<List<EsercizioGiornalieroViewDTO>> getSpecificDailyExerciseBySchedaAllenamento(@PathVariable(required = true, name = "id") int id) throws EserciziGiornalieriException {
        List<EsercizioGiornalieroViewDTO> viewDto = eserciziGiornalieriService.findBySchedaAllenamento(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }

}
