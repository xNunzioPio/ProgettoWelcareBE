package com.wellcare.controller;

import com.wellcare.controller.dto.collaborazioni.CollaborazioniViewDTO;
import com.wellcare.controller.dto.nutrizionista.CustomerAdditionToNutritionistDTO;
import com.wellcare.controller.dto.nutrizionista.CustomerRemovalToNutritionistDTO;
import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroViewDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaUpdateDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaViewDTO;
import com.wellcare.controller.dto.response.SuccessResponseDTO;
import com.wellcare.exceptions.*;
import com.wellcare.service.cliente.ClienteService;
import com.wellcare.service.nutrizionista.NutrizionistaService;
import com.wellcare.service.pastiGiornalieri.PastiGiornalieriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping(path ="private/nutrizionista")
public class NutrizionistaController {

    @Autowired
    public NutrizionistaService service;
    @Autowired
    public ClienteService clienteService;
    @Autowired
    public PastiGiornalieriService pastiGiornalieriService;

    @GetMapping(path="/all")
    public ResponseEntity<List<NutrizionistaViewDTO>> getAllNutritionist() throws NutrizionistaException {
        List<NutrizionistaViewDTO> ListDto = service.findAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<NutrizionistaViewDTO> getSpecificNutritionist(@PathVariable(required = true, name = "id") int id) throws NutrizionistaException {
        NutrizionistaViewDTO viewDto = service.findById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @GetMapping(path="idUtente/{id}")
    public ResponseEntity<NutrizionistaViewDTO> getSpecificNutritionistByIdUser(@PathVariable(required = true, name = "id") int id) throws NutrizionistaException {
        NutrizionistaViewDTO viewDto = service.findByIdUtente(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }

    @PostMapping(path="/update/{id}")
    public ResponseEntity<NutrizionistaViewDTO> updateNutritionist(@RequestBody NutrizionistaUpdateDTO dto,@PathVariable(required = true, name = "id") int id) throws NutrizionistaException  {
        NutrizionistaViewDTO viewDTO=service.updateNutrizionista(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @GetMapping(path="/collaborazioni/idCliente/{id}")
    public ResponseEntity<CollaborazioniViewDTO> getCollaborationsByIdCliente(@PathVariable(required = true, name = "id") int id) throws ClienteException {
        CollaborazioniViewDTO viewDto = clienteService.getCollaborationsByIdCliente(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @PostMapping(path="/addCustomer/{id}")
    public ResponseEntity<NutrizionistaViewDTO> addCustomer(@RequestBody CustomerAdditionToNutritionistDTO dto, @PathVariable(required = true, name = "id") int id) throws NutrizionistaException  {
        NutrizionistaViewDTO viewDTO=service.acceptCustomerRequest(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @PostMapping(path="/removeCustomer/{id}")
    public ResponseEntity<NutrizionistaViewDTO> removeCustomer(@RequestBody CustomerRemovalToNutritionistDTO dto, @PathVariable(required = true, name = "id") int id) throws NutrizionistaException  {
        NutrizionistaViewDTO viewDTO=service.removeCustomer(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @PostMapping(path="/rejectCustomerRequest/{id}")
    public ResponseEntity<SuccessResponseDTO> rejectCustomerRequest(@RequestBody CustomerRemovalToNutritionistDTO dto, @PathVariable(required = true, name = "id") int id) throws NutrizionistaException {
        SuccessResponseDTO viewDTO=service.rejectCustomerRequest(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @GetMapping(path="/monitoraggio/idSchedaAlimentare/{id}")
    public ResponseEntity<List<PastoGiornalieroViewDTO>> getSpecificDailyMealBySchedaAlimentare(@PathVariable(required = true, name = "id") int id) throws PastiGiornalieriException {
        List<PastoGiornalieroViewDTO> viewDto = pastiGiornalieriService.findBySchedaAlimentare(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }




}
