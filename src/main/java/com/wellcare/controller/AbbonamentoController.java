package com.wellcare.controller;

import com.wellcare.controller.dto.abbonamento.AbbonamentoViewDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaUpdateDTO;
import com.wellcare.controller.dto.nutrizionista.NutrizionistaViewDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerUpdateDTO;
import com.wellcare.controller.dto.personalTrainer.PersonalTrainerViewDTO;
import com.wellcare.exceptions.NutrizionistaException;
import com.wellcare.exceptions.PersonalTrainerException;
import com.wellcare.service.abbonamento.AbbonamentoService;
import com.wellcare.exceptions.AbbonamentoException;
import com.wellcare.service.nutrizionista.NutrizionistaService;
import com.wellcare.service.personalTrainer.PersonalTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(path = "public/abbonamento")
public class AbbonamentoController {

    @Autowired
    public AbbonamentoService service;
    @Autowired
    public PersonalTrainerService personalTrainerService;
    @Autowired
    public NutrizionistaService nutrizionistaService;

    @GetMapping(path="/all")
    public ResponseEntity<List<AbbonamentoViewDTO>> getAllSubscription() throws AbbonamentoException {
        List<AbbonamentoViewDTO> ListDto = service.getAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<AbbonamentoViewDTO> getSpecificSubscription(@PathVariable(required = true, name = "id") int id) throws AbbonamentoException {
        AbbonamentoViewDTO viewDto = service.getById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @PostMapping(path="/updateNutrizionista/{id}")
    public ResponseEntity<NutrizionistaViewDTO> updateNutritionist(@RequestBody NutrizionistaUpdateDTO dto, @PathVariable(required = true, name = "id") int id) throws NutrizionistaException {
        NutrizionistaViewDTO viewDTO=nutrizionistaService.updateNutrizionista(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @PostMapping(path="/updatePersonalTrainer/{id}")
    public ResponseEntity<PersonalTrainerViewDTO> updatePersonalTrainer(@RequestBody PersonalTrainerUpdateDTO dto, @PathVariable(required = true, name = "id") int id) throws PersonalTrainerException , ParseException {
        PersonalTrainerViewDTO viewDTO=personalTrainerService.updatePersonalTrainer(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }

}
