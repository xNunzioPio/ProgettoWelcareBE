package com.wellcare.controller;



import com.wellcare.controller.dto.eserciziGiornalieri.DataDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroCreationDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroUpdateDTO;
import com.wellcare.controller.dto.eserciziGiornalieri.EsercizioGiornalieroViewDTO;
import com.wellcare.exceptions.EserciziGiornalieriException;
import com.wellcare.service.eserciziGiornalieri.EserciziGiornalieriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path ="private/eserciziGiornalieri")
public class EserciziGiornalieriController {

    @Autowired
    public EserciziGiornalieriService service;

    @GetMapping(path="/all")
    public ResponseEntity<List<EsercizioGiornalieroViewDTO>> getAllDailyExercise() throws EserciziGiornalieriException {
        List<EsercizioGiornalieroViewDTO> ListDto = service.findAll();
        return new ResponseEntity<>(ListDto, HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<EsercizioGiornalieroViewDTO> getSpecificDailyExercise(@PathVariable(required = true, name = "id") int id) throws EserciziGiornalieriException {
        EsercizioGiornalieroViewDTO viewDto = service.findById(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @PostMapping(path="/create")
    public ResponseEntity<EsercizioGiornalieroViewDTO> createDailyExercise(@RequestBody EsercizioGiornalieroCreationDTO dto) throws EserciziGiornalieriException {
        EsercizioGiornalieroViewDTO viewDTO=service.createEsercizioGiornaliero(dto);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @DeleteMapping(path="/remove/{id}")
    public ResponseEntity<String> removeDailyExercise(@PathVariable(required = true, name = "id") int id)  {
        service.removeEsercizioGiornaliero(id);
        return new ResponseEntity<>("Eliminazione con successo!", HttpStatus.OK);
    }
    @PostMapping(path="/update/{id}")
    public ResponseEntity<EsercizioGiornalieroViewDTO> updateSpecificDailyExercise(@RequestBody EsercizioGiornalieroUpdateDTO dto, @PathVariable(required = true, name = "id") int id) throws EserciziGiornalieriException {
        EsercizioGiornalieroViewDTO viewDTO=service.updateEsercizioGiornaliero(dto,id);
        return new ResponseEntity<>(viewDTO, HttpStatus.OK);
    }
    @GetMapping(path="/idSchedaAllenamento/{id}")
    public ResponseEntity<List<EsercizioGiornalieroViewDTO>> getSpecificDailyExerciseBySchedaAllenamento(@PathVariable(required = true, name = "id") int id) throws EserciziGiornalieriException {
        List<EsercizioGiornalieroViewDTO> viewDto = service.findBySchedaAllenamento(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @GetMapping(path="/dailyExercise/idCliente/{id}")
    public ResponseEntity<List<EsercizioGiornalieroViewDTO>> getSpecificDailyExerciseByIdCliente(@PathVariable(required = true, name = "id") int id) throws EserciziGiornalieriException {
        List<EsercizioGiornalieroViewDTO> viewDto = service.findByIdCliente(id);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
    @GetMapping(path="/dailyExerciseByData/idCliente/{id},{data}")
    public ResponseEntity<List<EsercizioGiornalieroViewDTO>> getSpecificDailyExerciseByIdClienteAndData(@PathVariable(required = true, name = "id") int id , @PathVariable (required = true, name = "data") String data)  {
        List<EsercizioGiornalieroViewDTO> viewDto = service.findByDataCreazione(id,data);
        return new ResponseEntity<>(viewDto, HttpStatus.OK);
    }
}
