package com.wellcare.controller;

import com.wellcare.controller.dto.cliente.RichiestaPrestazioneNutrizionistaDTO;
import com.wellcare.controller.dto.cliente.RichiestaPrestazionePersonalTrainerDTO;
import com.wellcare.controller.dto.registration.ClienteCreationDTO;
import com.wellcare.controller.dto.cliente.ClienteUpdateDTO;
import com.wellcare.controller.dto.cliente.ClienteViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.exceptions.PersonalTrainerException;
import com.wellcare.service.cliente.ClienteService;
import com.wellcare.exceptions.ClienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "private/cliente")
public class ClienteController {

    @Autowired
    public ClienteService service;

    @GetMapping(path="/all")
    public ResponseEntity<List<ClienteViewDTO>> getAllCustomers() throws ClienteException {
        List<ClienteViewDTO> ListDto = service.findAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<ClienteViewDTO> getSpecificCustomer(@PathVariable(required = true, name = "id") int id) throws ClienteException {
        ClienteViewDTO viewDto = service.findById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @GetMapping(path="idUtente/{id}")
    public ResponseEntity<ClienteViewDTO> getSpecificCustomerByIdUser(@PathVariable(required = true, name = "id") int id) throws ClienteException {
        ClienteViewDTO viewDto = service.findByIdUtente(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }

    @PostMapping(path="/update/{id}")
    public ResponseEntity<ClienteViewDTO> updateCustomer(@RequestBody ClienteUpdateDTO dto, @PathVariable(required = true, name = "id") int id) throws ClienteException {
        ClienteViewDTO viewDTO=service.updateCliente(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }

    @PostMapping(path="/richiestaPrestazioneNutr/{id}")
    public ResponseEntity<SuccessResponseDTO> requestNutritionistService(@RequestBody RichiestaPrestazioneNutrizionistaDTO dto, @PathVariable(required = true, name = "id") int id) throws NutrizionistaException {
        service.richiestaPrestazioneNutr(dto,id);
        return new ResponseEntity<>(new SuccessResponseDTO("Richiesta Effettuata!"), HttpStatus.OK);
    }
    @PostMapping(path="/richiestaPrestazionePt/{id}")
    public ResponseEntity<SuccessResponseDTO> requestPersonalTrainerService(@RequestBody RichiestaPrestazionePersonalTrainerDTO dto, @PathVariable(required = true, name = "id") int id) throws PersonalTrainerException {
        service.richiestaPrestazionePT(dto,id);
        return new ResponseEntity<>(new SuccessResponseDTO("Richiesta Effettuata!"), HttpStatus.OK);
    }


}
