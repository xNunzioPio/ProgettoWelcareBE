package com.wellcare.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroCreationDTO;
import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroUpdateDTO;
import com.wellcare.controller.dto.pastiGiornalieri.PastoGiornalieroViewDTO;
import com.wellcare.exceptions.PastiGiornalieriException;
import com.wellcare.service.pastiGiornalieri.PastiGiornalieriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path ="private/pastiGiornalieri")
public class PastiGiornalieriController {

    @Autowired
    public PastiGiornalieriService service;

    @GetMapping(path="/all")
    public ResponseEntity<List<PastoGiornalieroViewDTO>> getAllDailyMeal() throws PastiGiornalieriException {
        List<PastoGiornalieroViewDTO> ListDto = service.findAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<PastoGiornalieroViewDTO> getSpecificDailyMeal(@PathVariable(required = true, name = "id") int id) throws PastiGiornalieriException {
        PastoGiornalieroViewDTO viewDto = service.findById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @PostMapping(path="/create")
    public ResponseEntity<PastoGiornalieroViewDTO> createDailyMeal(@RequestBody PastoGiornalieroCreationDTO dto)  {
        PastoGiornalieroViewDTO viewDTO=service.createPastoGiornaliero(dto);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @DeleteMapping(path="/remove/{id}")
    public ResponseEntity<String> removeDailyMeal(@PathVariable(required = true, name = "id") int id)  {
        service.removePastoGiornaliero(id);
        return new ResponseEntity<>("Eliminazione con successo!", HttpStatus.OK);
    }
    @PostMapping(path="/update/{id}")
    public ResponseEntity<PastoGiornalieroViewDTO> updateSpecificDailyMeal(@RequestBody PastoGiornalieroUpdateDTO dto, @PathVariable(required = true, name = "id") int id) throws PastiGiornalieriException {
        PastoGiornalieroViewDTO viewDTO=service.updatePastoGiornaliero(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @GetMapping(path="/idSchedaAlimentare/{id}")
    public ResponseEntity<List<PastoGiornalieroViewDTO>> getSpecificDailyMealBySchedaAlimentare(@PathVariable(required = true, name = "id") int id) throws PastiGiornalieriException {
        List<PastoGiornalieroViewDTO> viewDto = service.findBySchedaAlimentare(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }

    @GetMapping(path="/idCliente/{id}")
    public ResponseEntity<List<PastoGiornalieroViewDTO>> getDailyMealByCustomerId(@PathVariable(required = true, name = "id") int id) throws PastiGiornalieriException {
        List<PastoGiornalieroViewDTO> viewDto = service.findByIdCliente(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }

    @GetMapping(path="/idClienteDataCreazione/{id},{data}")
    public ResponseEntity<List<PastoGiornalieroViewDTO>> getMealByDataCreazione(@PathVariable("data") String data, @PathVariable(required = true, name = "id") int id) throws PastiGiornalieriException {
        List<PastoGiornalieroViewDTO> viewDto = service.findByDataCreazione(id,data);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
}
