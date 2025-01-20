package com.wellcare.controller;

import com.wellcare.controller.dto.esercizio.EsercizioCreationDTO;
import com.wellcare.controller.dto.esercizio.EsercizioViewDTO;
import com.wellcare.exceptions.EsercizioException;
import com.wellcare.service.schedaAllenamento.EsercizioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(path = "private/esercizio")
public class EsercizioController {

    @Autowired
    public EsercizioService service;

    @GetMapping(path="/all")
    public ResponseEntity<List<EsercizioViewDTO>> getAllExercise() throws EsercizioException {
        List<EsercizioViewDTO> ListDto = service.findAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<EsercizioViewDTO> getSpecificExerciseById(@PathVariable(required = true, name = "id") int id) throws EsercizioException {
        EsercizioViewDTO viewDto = service.findById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }

    @GetMapping(path="/{nome}")
    public ResponseEntity<List<EsercizioViewDTO>> getSpecificExercuseByName(@PathVariable(required = true, name = "nome") String nome) throws EsercizioException {
        List<EsercizioViewDTO> viewDto = service.findByNome(nome);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @PostMapping(path="/create")
    public ResponseEntity<EsercizioViewDTO> createExercise(EsercizioCreationDTO dto) throws EsercizioException {
        EsercizioViewDTO viewDTO=service.createEsercizio(dto);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
}
